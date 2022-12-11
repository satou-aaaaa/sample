package domein.model.user

import java.util.*

interface UserIdRepository {

    fun add(userId: UUID)

    fun validatedIdExists(userId: UUID)
}