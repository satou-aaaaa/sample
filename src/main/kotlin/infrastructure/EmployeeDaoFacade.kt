package infrastructure

import domein.model.Employee
import java.time.LocalDateTime
import java.util.UUID
import kotlin.IllegalStateException

class EmployeeDaoFacade(
    private val employeeDao: EmployeeDao
) {
    private var employeeNameDao: EmployeeNameDao = EmployeeNameDao(employeeDao)
    private var employeeStatusDao: EmployeeStatusDao = EmployeeStatusDao(employeeDao)

    fun insert(employee: Employee) {
        validatedEmployeeNotAdded(employee)

        employeeDao.insert(
            EmployeeDto(
                employeeId = employee.employeeId,
                createdAt = LocalDateTime.now(),
            )
        )

        employeeNameDao.insert(
            EmployeeNameDto(
                employeeId = employee.employeeId,
                name = employee.name,
                createdAt = LocalDateTime.now()
            )
        )

        employeeStatusDao.insert(
            EmployeeStatusDto(
                employeeId = employee.employeeId,
                status = EmployeeStatus.NOT_DELETED,
                createdAt = LocalDateTime.now()
            )
        )
    }

    private fun validatedEmployeeNotAdded(employee: Employee) {
        val existing = employeeDao.selectOrNull(employee.employeeId)
        if (existing != null) {
            throw IllegalStateException("既に追加されている社員です。")
        }
    }

    fun update(employee: Employee) {
        insertEmployeeDtoIfNotExists(employee)

        val existingNameDto = employeeNameDao.selectLatestOrNull(employee.employeeId)
            ?: throw IllegalStateException("社員の氏名が存在しません。")
        insertIfNameIsNotSame(existingNameDto, employee)

        val existingStatusDto = employeeStatusDao.selectLatestOrNull(employee.employeeId)
            ?: throw IllegalStateException("社員のstatusが存在しません。")

        employeeStatusDao.insert(
            EmployeeStatusDto(
                employeeId = existingStatusDto.employeeId,
                status = EmployeeStatus.NOT_DELETED,
                createdAt = LocalDateTime.now()
            )
        )
    }

    private fun insertIfNameIsNotSame(existingNameDto: EmployeeNameDto, employee: Employee) {
        if (existingNameDto.name != employee.name) {
            employeeNameDao.insert(
                EmployeeNameDto(
                    employeeId = existingNameDto.employeeId,
                    name = employee.name,
                    createdAt = LocalDateTime.now()
                )
            )
        }
    }

    private fun insertEmployeeDtoIfNotExists(employee: Employee) {
        val existingEmployeeDto = employeeDao.selectOrNull(employee.employeeId)
        if (existingEmployeeDto == null) {
            employeeDao.insert(
                EmployeeDto(
                    employeeId = employee.employeeId,
                    createdAt = LocalDateTime.now()
                )
            )
        }
    }

    fun selectBy(employeeId: UUID): Employee {
        val employeeDto = employeeDao.selectOrNull(employeeId)
            ?: throw IllegalStateException("社員が存在しません。 employeeId => $employeeId")
        val nameDto = employeeNameDao.selectLatestOrNull(employeeId)
            ?: throw IllegalStateException("社員が存在しません。 employeeId => $employeeId")
        val statusDto = employeeStatusDao.selectLatestOrNull(employeeId)
            ?: throw IllegalStateException("社員が存在しません。 employeeId => $employeeId")

        return Employee(
            employeeId = employeeDto.employeeId,
            name = nameDto.name,
            status = statusDto.status
        )
    }
}
