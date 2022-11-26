package infrastructure

import domein.model.Employee
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.IllegalStateException
import java.lang.Thread.sleep
import java.util.*

private class EmployeeDaoFacadeTest {

    private val sut = EmployeeDaoFacade(
        employeeDao = EmployeeDao()
    )

    @Test
    fun `追加した社員が検索できること`() {
        val employeeId = UUID.fromString("69b8336d-c634-43df-8414-40c6bda10a06")

        val employee = Employee(
            employeeId = employeeId,
            name = "佐藤",
            status = EmployeeStatus.ACTIVE_DUTY
        )
        sut.insert(employee)

        val actual = sut.selectOrNull(employeeId)

        assertEquals(employee, actual)
    }

    @Test
    fun `社員が存在しない場合は例外を投げること`() {
        val notExistsEmployeeId = UUID.fromString("32d3c6b1-250a-42d2-ad8e-a4ff196cccb6")

        assertThrows<IllegalStateException> {
            sut.selectBy(notExistsEmployeeId)
        }
    }

    @Test
    fun `複数回社員を追加した場合、最新のデータが検索できること`() {
        val employeeId = UUID.fromString("3e921828-3706-4b5f-844c-20dd0fd36603")

        val employee1 = Employee(
            employeeId = employeeId,
            name = "佐藤",
            status = EmployeeStatus.ACTIVE_DUTY
        )
        sut.insert(employee1)

        sleep(1)

        val employee2 = employee1.copy(
            name = "田中",
            status = EmployeeStatus.RETIRED
        )
        sut.update(employee2)

        val actual = sut.selectOrNull(employee1.employeeId)

        assertEquals(employee2, actual)
    }

    @Test
    fun `同じ社員を複数回追加しようとした場合は例外を投げる`() {
        val employeeId = UUID.fromString("96174a80-bcc5-4c03-a790-8141af563598")

        val employee = Employee(
            employeeId = employeeId,
            name = "佐藤",
            status = EmployeeStatus.ACTIVE_DUTY
        )
        sut.insert(employee)

        assertThrows<IllegalStateException> {
            sut.insert(employee)
        }
    }

    @Test
    fun `追加されていない社員をupdateしようとした場合には例外を投げる`() {
        val employeeId = UUID.fromString("1a40efdc-3f89-4ab8-b139-6edc55bfd908")

        val employee = Employee(
            employeeId = employeeId,
            name = "佐藤",
            status = EmployeeStatus.ACTIVE_DUTY
        )

        assertThrows<IllegalStateException> {
            sut.update(employee)
        }
    }

}
