package domein.model.employee

import java.util.*

interface EmployeeRepository {

    fun resolveBy(employeeId: UUID): Employee

    fun add(employee: Employee)

    fun replace(employee: Employee)

    fun remove(employeeId: UUID)
}
