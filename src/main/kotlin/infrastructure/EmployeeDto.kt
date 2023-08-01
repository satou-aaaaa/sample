package infrastructure

import java.time.LocalDateTime
import java.util.UUID

data class EmployeeDto(
    val employeeId: UUID,
    val createdAt: LocalDateTime
)
