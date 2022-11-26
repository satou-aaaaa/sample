package infrastructure

import java.time.LocalDateTime
import java.util.*

data class EmployeeNameDto(
    val employeeId: UUID,
    val name:String,
    val createdAt:LocalDateTime,
)