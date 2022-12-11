package domein.model.user

import java.util.*

interface UserRealNameRepository {

    fun add(userId: UUID, realName: String)

    fun resolveBy(userId: UUID): String
}