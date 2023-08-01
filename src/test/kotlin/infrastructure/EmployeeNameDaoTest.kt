package infrastructure

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime
import java.util.UUID

private class EmployeeNameDaoTest {

    private val employeeDao: EmployeeDao = EmployeeDao()

    private val sut = EmployeeNameDao(employeeDao)

    @BeforeEach
    fun setup() {
        employeeDao.insert(addedEmployeeDto)
    }

    @Test
    fun `insertしたデータがselectメソッドで検索できること`() {
        val dto = EmployeeNameDto(
            employeeId = addedEmployeeId,
            name = "佐藤",
            createdAt = LocalDateTime.of(2020, 1, 1, 1, 1)
        )

        sut.insert(dto)

        val actual = sut.selectLatestOrNull(addedEmployeeId)

        assertEquals(dto, actual)
    }

    @Test
    fun `複数件insertされていた場合、selectメソッドで最新のデータが返ってくること`() {
        val dto1 = EmployeeNameDto(
            employeeId = addedEmployeeId,
            name = "佐藤",
            createdAt = LocalDateTime.of(2020, 1, 1, 1, 1)
        )
        sut.insert(dto1)
        val dto2 = EmployeeNameDto(
            employeeId = addedEmployeeId,
            name = "田中",
            createdAt = LocalDateTime.of(2020, 1, 1, 1, 2)
        )
        sut.insert(dto2)

        val actual = sut.selectLatestOrNull(addedEmployeeId)

        assertEquals(dto2, actual)
    }

    @Test
    fun `複数件insertされており、かつ日時の指定をした場合、selectメソッドで指定された日時時点の最新のデータが返ってくること`() {
        val dto1 = EmployeeNameDto(
            employeeId = addedEmployeeId,
            name = "佐藤",
            createdAt = LocalDateTime.of(2020, 1, 1, 1, 1)
        )
        sut.insert(dto1)
        val dto2 = EmployeeNameDto(
            employeeId = addedEmployeeId,
            name = "田中",
            createdAt = LocalDateTime.of(2020, 1, 1, 1, 2)
        )
        sut.insert(dto2)
        val dto3 = EmployeeNameDto(
            employeeId = addedEmployeeId,
            name = "中村",
            createdAt = LocalDateTime.of(2020, 1, 1, 1, 3)
        )
        sut.insert(dto3)

        val searchDateTime = dto3.createdAt.minusSeconds(1)

        val actual = sut.selectOrNull(
            employeeId = addedEmployeeId,
            time = searchDateTime
        )

        assertEquals(dto2, actual)
    }

    @Test
    fun `データが存在しなかった場合はnullを返すこと`() {
        val notExistingId = UUID.fromString("d6a6205d-728f-4fed-8c0f-e1f36f185f5b")

        val actual = sut.selectLatestOrNull(notExistingId)

        assertNull(actual)
    }

    @Test
    fun `employeeIdが存在しない場合は例外を投げること`() {
        val notExistingEmployeeId = UUID.fromString("34a1b832-2220-4075-a704-669c0e97cbbc")

        val dto = EmployeeNameDto(
            employeeId = notExistingEmployeeId,
            name = "佐藤",
            createdAt = LocalDateTime.now()
        )

        assertThrows<IllegalStateException> {
            sut.insert(dto)
        }
    }

    companion object {
        val addedEmployeeId: UUID = UUID.fromString("67b789c1-3f36-4300-9219-da3f18f22c16")
        val addedEmployeeDto = EmployeeDto(
            employeeId = addedEmployeeId,
            createdAt = LocalDateTime.now()
        )
    }
}
