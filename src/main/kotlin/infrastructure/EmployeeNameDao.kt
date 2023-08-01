package infrastructure

import java.time.LocalDateTime
import java.util.UUID

class EmployeeNameDao(
    private val employeeDao: EmployeeDao
) {

    private var employeeNameTable: MutableList<EmployeeNameDto> = mutableListOf()

    fun insert(dto: EmployeeNameDto) {
        validatedEmployeeTable(dto)

        employeeNameTable.add(dto)
    }

    private fun validatedEmployeeTable(dto: EmployeeNameDto) {
        (
            employeeDao.selectOrNull(dto.employeeId)
                ?: throw IllegalStateException("employeeIdが存在しません。")
            )
    }

    fun selectLatestOrNull(employeeId: UUID): EmployeeNameDto? {
        val existing = employeeNameTable.filter {
            it.employeeId == employeeId
        }.maxByOrNull {
            it.createdAt
        }

        return existing
    }

    fun selectOrNull(employeeId: UUID, time: LocalDateTime): EmployeeNameDto? {
        val existing = employeeNameTable.filter {
            it.employeeId == employeeId &&
                it.createdAt < time
        }.maxByOrNull {
            it.createdAt
        }

        return existing
    }
}
