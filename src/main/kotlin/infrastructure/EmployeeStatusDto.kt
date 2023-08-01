package infrastructure

import java.time.LocalDateTime
import java.util.UUID

data class EmployeeStatusDto(
    val employeeId: UUID,
    val status: EmployeeStatus,
    val createdAt: LocalDateTime
)
