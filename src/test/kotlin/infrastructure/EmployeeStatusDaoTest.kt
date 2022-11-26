package infrastructure

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime
import java.util.*


private class EmployeeStatusDaoTest {

    private val employeeDao: EmployeeDao = EmployeeDao()

    private val sut = EmployeeStatusDao(employeeDao)

    @BeforeEach
    fun setup() {
        employeeDao.insert(addedEmployeeDto)
    }

    @Test
    fun `insertしたデータがselectメソッドで検索できること`() {
        val dto = EmployeeStatusDto(
            employeeId = addedEmployeeId,
            status = EmployeeStatus.RETIRED,
            createdAt = LocalDateTime.of(2020, 1, 1, 1, 1),
        )

        sut.insert(dto)

        val actual = sut.selectLatestOrNull(addedEmployeeId)

        assertEquals(dto, actual)
    }

    @Test
    fun `複数件insertされていた場合、selectメソッドで最新のデータが返ってくること`() {
        val dto1 = EmployeeStatusDto(
            employeeId = addedEmployeeId,
            status = EmployeeStatus.RETIRED,
            createdAt = LocalDateTime.of(2020, 1, 1, 1, 1),
        )
        sut.insert(dto1)
        val dto2 = EmployeeStatusDto(
            employeeId = addedEmployeeId,
            status = EmployeeStatus.RETIRED,
            createdAt = LocalDateTime.of(2020, 1, 1, 1, 2),
        )
        sut.insert(dto2)

        val actual = sut.selectLatestOrNull(addedEmployeeId)

        assertEquals(dto2, actual)
    }

    @Test
    fun `複数件insertされており、かつ日時の指定をした場合、selectメソッドで指定された日時時点の最新のデータが返ってくること`() {
        val dto1 = EmployeeStatusDto(
            employeeId = addedEmployeeId,
            status = EmployeeStatus.RETIRED,
            createdAt = LocalDateTime.of(2020, 1, 1, 1, 1),
        )
        sut.insert(dto1)
        val dto2 = EmployeeStatusDto(
            employeeId = addedEmployeeId,
            status = EmployeeStatus.ACTIVE_DUTY,
            createdAt = LocalDateTime.of(2020, 1, 1, 1, 2),
        )
        sut.insert(dto2)
        val dto3 = EmployeeStatusDto(
            employeeId = addedEmployeeId,
            status = EmployeeStatus.RETIRED,
            createdAt = LocalDateTime.of(2020, 1, 1, 1, 3),
        )
        sut.insert(dto3)

        val searchDateTime = dto3.createdAt.minusSeconds(1)

        val actual = sut.selectOrNull(
            employeeId = addedEmployeeId,
            time = searchDateTime,
        )

        assertEquals(dto2, actual)
    }

    @Test
    fun `データが存在しなかった場合はnullを返すこと`() {
        val notExistingId = UUID.fromString("6096d57c-4308-423f-a56e-641f36a76ffb")

        val actual = sut.selectLatestOrNull(notExistingId)

        assertNull(actual)
    }

    @Test
    fun `employeeIdが存在しない場合は例外を投げること`() {
        val notExistingEmployeeId = UUID.fromString("262f5554-f0eb-4689-913c-b8c0868d7d0e")

        val dto = EmployeeStatusDto(
            employeeId = notExistingEmployeeId,
            status = EmployeeStatus.RETIRED,
            createdAt = LocalDateTime.now(),
        )

        assertThrows<IllegalStateException> {
            sut.insert(dto)
        }
    }

    companion object {
        val addedEmployeeId: UUID = UUID.fromString("7d2830ec-07fa-4e01-8882-97d67f0db97a")
        val addedEmployeeDto = EmployeeDto(
            employeeId = addedEmployeeId,
            createdAt = LocalDateTime.now(),
        )
    }
}