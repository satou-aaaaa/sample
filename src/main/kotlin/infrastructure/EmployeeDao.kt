package infrastructure

import java.time.LocalDateTime
import java.util.*

class EmployeeDao {

    private var employee: MutableMap<UUID, LocalDateTime> = mutableMapOf()

    fun insert(dto: EmployeeDto) {
        LocalDateTime.now()
        val existing = this.selectOrNull(dto.employeeId)
        if (existing != null) {
            throw IllegalStateException("既に存在しています")
        }

        employee.put(
            key = dto.employeeId,
            value = dto.createdAt
        )
    }

    fun selectOrNull(employeeId: UUID): EmployeeDto? {
        val createdAt = employee.get(employeeId)
            ?: return null

        return EmployeeDto(
            employeeId = employeeId,
            createdAt = createdAt
        )
    }
}
