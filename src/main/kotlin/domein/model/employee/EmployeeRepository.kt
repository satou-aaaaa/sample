package domein.model.employee

import java.util.UUID

interface EmployeeRepository {

    fun selectBy(employeeId: UUID): Employee

    fun add(employee: Employee)

    fun replace(employee: Employee)

    fun remove(employeeId: UUID)
}
