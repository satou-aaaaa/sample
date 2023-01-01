package infrastructure

import domein.model.employee.Employee
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.Thread.sleep
import java.util.*

private class EmployeeRepositoryTest {

    private val employeeDao: EmployeeDao = EmployeeDao()

    private val sut = EmployeeRepositoryImpl(
        employeeDaoFacade = EmployeeDaoFacade(employeeDao)
    )

    @Test
    fun `追加した社員が検索できること`() {
        val employeeId = UUID.fromString("aca88a13-19d6-4255-98e0-3b3f99902b9b")

        val employee = Employee(
            employeeId = employeeId,
            name = "佐藤"
        )
        sut.add(employee)

        val actual = sut.resolveBy(employeeId)

        assertEquals(employee, actual)
    }

    @Test
    fun `社員が存在しない場合は例外を投げること`() {
        val notExistsEmployeeId = UUID.fromString("4b1056a0-1aa8-4738-b006-5f87f33f6adf")

        assertThrows<IllegalStateException> {
            sut.resolveBy(notExistsEmployeeId)
        }
    }

    @Test
    fun `データを更新した場合、最新のデータが検索できること`() {
        val employeeId = UUID.fromString("8915b481-9153-4c11-a254-0484eec0c135")

        val employee1 = Employee(
            employeeId = employeeId,
            name = "佐藤"
        )
        sut.add(employee1)

        sleep(1)

        val employee2 = employee1.copy(
            name = "田中"
        )
        sut.replace(employee2)

        val actual = sut.resolveBy(employee1.employeeId)

        assertEquals(employee2, actual)
    }

    @Test
    fun `同じ社員を複数回追加しようとした場合は例外を投げる`() {
        val employeeId = UUID.fromString("52e77f54-5c80-466e-b05c-e8bca743fc5a")

        val employee = Employee(
            employeeId = employeeId,
            name = "佐藤"
        )
        sut.add(employee)

        assertThrows<IllegalStateException> {
            sut.add(employee)
        }
    }

    @Test
    fun `追加されていない社員を更新しようとした場合には例外を投げる`() {
        val employeeId = UUID.fromString("8ee29ce3-e042-4bbe-855d-ad207ecb0b03")

        val employee = Employee(
            employeeId = employeeId,
            name = "佐藤"
        )

        assertThrows<IllegalStateException> {
            sut.replace(employee)
        }
    }

    @Test
    fun `削除された社員を検索しようとした場合には例外を投げる`() {
        val employeeId = UUID.fromString("a7edc254-c410-4866-ad32-0d3132f777e2")

        val employee = Employee(
            employeeId = employeeId,
            name = "佐藤"
        )

        sut.add(employee)

        sleep(1)

        sut.remove(employee.employeeId)

        assertThrows<IllegalStateException> {
            sut.resolveBy(employee.employeeId)
        }
    }

    @Test
    fun `削除された社員を更新しようとした場合には例外を投げる`() {
        val employeeId = UUID.fromString("10b46997-190a-4ab7-9bbc-ec65414f13e4")

        val inserting = Employee(
            employeeId = employeeId,
            name = "佐藤"
        )

        sut.add(inserting)

        sleep(1)

        sut.remove(inserting.employeeId)

        val updating = Employee(
            employeeId = employeeId,
            name = "田中"
        )

        sut.replace(updating)

        assertThrows<IllegalStateException> {
            sut.resolveBy(updating.employeeId)
        }
    }

    @Test
    fun `削除された社員を追加しようとした場合には例外を投げる`() {
        val employeeId = UUID.fromString("8c27d61a-d3d6-4b37-ad7a-7f06fee3dd35")

        val inserting = Employee(
            employeeId = employeeId,
            name = "佐藤"
        )

        sut.add(inserting)

        sleep(1)

        sut.remove(inserting.employeeId)

        assertThrows<IllegalStateException> {
            sut.add(inserting)
        }
    }

    @Test
    fun `削除された社員を削除しようとした場合には例外を投げる`() {
        val employeeId = UUID.fromString("afd5b57b-03b5-4164-8b49-d2771bbf5fc5")

        val inserting = Employee(
            employeeId = employeeId,
            name = "佐藤"
        )

        sut.add(inserting)

        sleep(1)

        sut.remove(inserting.employeeId)

        assertThrows<IllegalStateException> {
            sut.remove(inserting.employeeId)
        }
    }

    @Test
    fun `存在しない社員を削除しようとした場合には例外を投げる`() {
        val employeeId = UUID.fromString("ffe7a0ec-bb22-493b-94a8-dd0c451f5be7")

        assertThrows<IllegalStateException> {
            sut.remove(employeeId)
        }
    }
}
