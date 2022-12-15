package infrastructure

import domein.model.employee.Employee
import java.time.LocalDateTime
import java.util.*

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
                createdAt = LocalDateTime.now()
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
            throw IllegalStateException("既に追加されている社員です。employeeId => ${employee.employeeId}")
        }
    }

    fun update(employee: Employee) {
        insertEmployeeDtoIfNotExists(employee.employeeId)
        insertIfNameChanged(employee.employeeId, employee.name)
        insertStatusNotDeleted(employee.employeeId)
    }

    private fun insertStatusNotDeleted(employeeId: UUID) {
        val existingStatusDto = employeeStatusDao.selectLatestOrNull(employeeId)
            ?: throw IllegalStateException("社員のstatusが存在しません。employeeId => $employeeId")
        employeeStatusDao.insert(
            EmployeeStatusDto(
                employeeId = existingStatusDto.employeeId,
                status = EmployeeStatus.NOT_DELETED,
                createdAt = LocalDateTime.now()
            )
        )
    }

    private fun insertIfNameChanged(employeeId: UUID, name: String) {
        val existingNameDto = employeeNameDao.selectLatestOrNull(employeeId)
            ?: throw IllegalStateException("社員の氏名が存在しません。employeeId => $employeeId")
        if (existingNameDto.name != name) {
            employeeNameDao.insert(
                EmployeeNameDto(
                    employeeId = existingNameDto.employeeId,
                    name = name,
                    createdAt = LocalDateTime.now()
                )
            )
        }
    }

    private fun insertEmployeeDtoIfNotExists(employeeId: UUID) {
        val existingEmployeeDto = employeeDao.selectOrNull(employeeId)
        if (existingEmployeeDto == null) {
            employeeDao.insert(
                EmployeeDto(
                    employeeId = employeeId,
                    createdAt = LocalDateTime.now()
                )
            )
        }
    }

    fun selectBy(employeeId: UUID): Employee {
        val employeeDto = employeeDao.selectOrNull(employeeId)
            ?: throwEmployeeNotFound(employeeId)
        val nameDto = employeeNameDao.selectLatestOrNull(employeeId)
            ?: throwEmployeeNotFound(employeeId)
        val statusDto = employeeStatusDao.selectLatestOrNull(employeeId)
            ?: throwEmployeeNotFound(employeeId)

        if (statusDto.status == EmployeeStatus.DELETED) {
            throwEmployeeNotFound(employeeId)
        }

        return Employee(
            employeeId = employeeDto.employeeId,
            name = nameDto.name
        )
    }

    private fun throwEmployeeNotFound(employeeId: UUID): Nothing {
        throw IllegalStateException("社員が存在しません。 employeeId => $employeeId")
    }

    fun delete(employeeId: UUID) {
        val existing = employeeStatusDao.selectLatestOrNull(employeeId)
            ?: throwEmployeeNotFound(employeeId)

        if (existing.status == EmployeeStatus.DELETED) {
            throwEmployeeNotFound(employeeId)
        }

        val dto = EmployeeStatusDto(
            employeeId = employeeId,
            status = EmployeeStatus.DELETED,
            createdAt = LocalDateTime.now()
        )
        employeeStatusDao.insert(dto)
    }
}
