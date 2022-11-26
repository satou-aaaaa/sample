package infrastructure

import java.time.LocalDateTime
import java.util.*

data class EmployeeStatusDto(
    val employeeId: UUID,
    val status: EmployeeStatus,
    val createdAt:LocalDateTime,
)