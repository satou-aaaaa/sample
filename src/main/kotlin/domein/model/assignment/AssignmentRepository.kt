package domein.model.assignment

import java.util.UUID

interface AssignmentRepository {

    fun resolveBy(employeeId: UUID, departmentId: UUID): Assignment

    fun add(assignment: Assignment)

    fun replace(assignment: Assignment)

    fun remove(employeeId: UUID, departmentId: UUID)
}
