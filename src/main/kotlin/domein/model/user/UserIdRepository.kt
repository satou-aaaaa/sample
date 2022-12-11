package domein.model.user

import java.util.*

interface UserIdRepository {

    fun addIfAbsent(userId: UUID)

    fun validatedIdExists(userId: UUID)
}