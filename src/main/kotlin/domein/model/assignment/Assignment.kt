package domein.model.assignment

import java.util.*

data class Assignment(
    val assignmentId:UUID,
    val employeeId: UUID,
    val departmentId:UUID,
    val reason:String,
)
