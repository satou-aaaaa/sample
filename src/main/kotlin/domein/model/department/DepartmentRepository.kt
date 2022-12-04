package domein.model.department

import java.util.*

interface DepartmentRepository {
    fun selectBy(departmentId: UUID): Department

    fun add(department: Department)

    fun replace(department: Department)

    fun remove(departmentId: UUID)
}