package infrastructure

import java.time.LocalDateTime
import java.util.*

class EmployeeStatusDao(
    private val employeeDao: EmployeeDao
) {

    private var employeeStatusTable: MutableList<EmployeeStatusDto> = mutableListOf()

    fun insert(dto: EmployeeStatusDto) {
        validatedEmployeeTable(dto)

        employeeStatusTable.add(dto)
    }

    private fun validatedEmployeeTable(dto: EmployeeStatusDto) {
        (
            employeeDao.selectOrNull(dto.employeeId)
                ?: throw IllegalStateException("employeeIdが存在しません。")
            )
    }

    fun selectLatestOrNull(employeeId: UUID): EmployeeStatusDto? {
        val existing = employeeStatusTable.filter {
            it.employeeId == employeeId
        }.maxByOrNull {
            it.createdAt
        }
        return existing
    }

    fun selectOrNull(employeeId: UUID, time: LocalDateTime): EmployeeStatusDto? {
        val existing = employeeStatusTable.filter {
            it.employeeId == employeeId &&
                it.createdAt < time
        }.maxByOrNull {
            it.createdAt
        }

        return existing
    }
}
