package infrastructure

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime
import java.util.UUID

private class EmployeeDaoTest {

    private val sut = EmployeeDao()

    @Test
    fun `insertしたデータがselectメソッドで検索できること`() {
        val dto = EmployeeDto(
            employeeId = UUID.fromString("34a1b832-2220-4075-a704-669c0e97cbbc"),
            createdAt = LocalDateTime.now()
        )

        sut.insert(dto)

        val actual = sut.selectOrNull(dto.employeeId)

        assertEquals(dto, actual)
    }

    @Test
    fun `同じデータがinsertされた場合は例外を投げること`() {
        val dto = EmployeeDto(
            employeeId = UUID.fromString("6b4635ba-b868-457b-9109-fe9fc4db1682"),
            createdAt = LocalDateTime.now()
        )

        sut.insert(dto)

        assertThrows<IllegalStateException> {
            sut.insert(dto)
        }
    }

    @Test
    fun `データが存在しなかった場合はnullを返すこと`() {
        val notExistingId = UUID.fromString("d6a6205d-728f-4fed-8c0f-e1f36f185f5b")

        val actual = sut.selectOrNull(notExistingId)

        assertNull(actual)
    }
}
