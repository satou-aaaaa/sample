package domein.model.user

import java.util.UUID

interface UserIdRepository {

    fun add(userId: UUID)

    fun validatedExists(userId: UUID)
}
