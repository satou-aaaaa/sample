package usecase.user

import domein.model.user.UserIdRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class CreateUserId(
    private val userIdRepository: UserIdRepository,
) {

    fun handle(): UUID {
        val userId = UUID.randomUUID()

        userIdRepository.add(userId)

        return userId
    }
}
