package domein.model.user

import java.util.UUID

interface UserRealNameRepository {

    fun add(userId: UUID, realName: String)

    fun replace(userId: UUID, realName: String)

    fun remove(userId: UUID)

    fun resolveBy(userId: UUID): String
}
