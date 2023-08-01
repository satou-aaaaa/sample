package infrastructure

import domein.model.employee.Employee
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.Thread.sleep
import java.util.UUID

private class EmployeeDaoFacadeTest {

    private val sut = EmployeeDaoFacade(
        employeeDao = EmployeeDao()
    )

    @Test
    fun `追加した社員が検索できること`() {
        val employeeId = UUID.fromString("69b8336d-c634-43df-8414-40c6bda10a06")

        val employee = Employee(
            employeeId = employeeId,
            name = "佐藤"
        )
        sut.insert(employee)

        val actual = sut.selectBy(employeeId)

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
    fun `データを更新した場合、最新のデータが検索できること`() {
        val employeeId = UUID.fromString("3e921828-3706-4b5f-844c-20dd0fd36603")

        val employee1 = Employee(
            employeeId = employeeId,
            name = "佐藤"
        )
        sut.insert(employee1)

        sleep(1)

        val employee2 = employee1.copy(
            name = "田中"
        )
        sut.update(employee2)

        val actual = sut.selectBy(employee1.employeeId)

        assertEquals(employee2, actual)
    }

    @Test
    fun `同じ社員を複数回追加しようとした場合は例外を投げる`() {
        val employeeId = UUID.fromString("96174a80-bcc5-4c03-a790-8141af563598")

        val employee = Employee(
            employeeId = employeeId,
            name = "佐藤"
        )
        sut.insert(employee)

        assertThrows<IllegalStateException> {
            sut.insert(employee)
        }
    }

    @Test
    fun `追加されていない社員を更新しようとした場合には例外を投げる`() {
        val employeeId = UUID.fromString("1a40efdc-3f89-4ab8-b139-6edc55bfd908")

        val employee = Employee(
            employeeId = employeeId,
            name = "佐藤"
        )

        assertThrows<IllegalStateException> {
            sut.update(employee)
        }
    }

    @Test
    fun `削除された社員を検索しようとした場合には例外を投げる`() {
        val employeeId = UUID.fromString("ce1019c1-0352-475f-8732-99bc54ee28cf")

        val employee = Employee(
            employeeId = employeeId,
            name = "佐藤"
        )

        sut.insert(employee)

        sleep(1)

        sut.delete(employee.employeeId)

        assertThrows<IllegalStateException> {
            sut.selectBy(employee.employeeId)
        }
    }

    @Test
    fun `削除された社員を更新しようとした場合には例外を投げる`() {
        val employeeId = UUID.fromString("a00e2b7f-2ea4-464e-a972-3341bf0b4711")

        val inserting = Employee(
            employeeId = employeeId,
            name = "佐藤"
        )

        sut.insert(inserting)

        sleep(1)

        sut.delete(inserting.employeeId)

        val updating = Employee(
            employeeId = employeeId,
            name = "田中"
        )

        sut.update(updating)

        assertThrows<IllegalStateException> {
            sut.selectBy(updating.employeeId)
        }
    }

    @Test
    fun `削除された社員を追加しようとした場合には例外を投げる`() {
        val employeeId = UUID.fromString("39fcce89-9e11-45e1-a89c-2ce07cc22334")

        val inserting = Employee(
            employeeId = employeeId,
            name = "佐藤"
        )

        sut.insert(inserting)

        sleep(1)

        sut.delete(inserting.employeeId)

        assertThrows<IllegalStateException> {
            sut.insert(inserting)
        }
    }

    @Test
    fun `削除された社員を削除しようとした場合には例外を投げる`() {
        val employeeId = UUID.fromString("382e011e-6006-4cae-b0df-8e99e87e94cb")

        val inserting = Employee(
            employeeId = employeeId,
            name = "佐藤"
        )

        sut.insert(inserting)

        sleep(1)

        sut.delete(inserting.employeeId)

        assertThrows<IllegalStateException> {
            sut.delete(inserting.employeeId)
        }
    }

    @Test
    fun `存在しない社員を削除しようとした場合には例外を投げる`() {
        val employeeId = UUID.fromString("10e57335-5f6c-483d-8708-e7df373d29e6")

        assertThrows<IllegalStateException> {
            sut.delete(employeeId)
        }
    }
}
