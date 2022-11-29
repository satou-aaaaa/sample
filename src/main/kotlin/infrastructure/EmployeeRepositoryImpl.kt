package infrastructure

import domein.model.employee.Employee
import domein.model.employee.EmployeeRepository
import java.time.LocalDateTime
import java.util.UUID
import kotlin.IllegalStateException

class EmployeeRepositoryImpl(
    private val employeeDaoFacade: EmployeeDaoFacade,
): EmployeeRepository {

    override fun selectBy(employeeId: UUID):Employee {
        return employeeDaoFacade.selectBy(employeeId)
    }

    override fun add(employee: Employee) {
        employeeDaoFacade.insert(employee)
    }

    override fun replace(employee: Employee) {
        employeeDaoFacade.update(employee)
    }

    override fun remove(employeeId: UUID) {
        employeeDaoFacade.delete(employeeId)
    }

}
