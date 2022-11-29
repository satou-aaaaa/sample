package domein.model

import infrastructure.EmployeeStatus
import java.util.*

data class Employee(
    val employeeId: UUID,
    val name:String,
) {
}