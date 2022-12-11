package domein.model.user

import java.util.*

interface UserIdRepository {

    fun add(userId: UUID)

    fun validatedExists(userId: UUID)
}