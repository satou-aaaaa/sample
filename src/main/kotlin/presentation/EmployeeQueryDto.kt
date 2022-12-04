package presentation

import java.util.UUID

data class EmployeeQueryDto(
    val employeeId:UUID,
    val name:String,
)
