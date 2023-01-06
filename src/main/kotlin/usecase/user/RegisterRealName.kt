package usecase.user

import domein.model.user.UserIdRepository
import domein.model.user.UserRealNameRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class RegisterRealName(
    private val userIdRepository: UserIdRepository,
    private val realNameRepository: UserRealNameRepository,
) {

    fun handle(input: RealNameRegisterInputData) {
        val userId = input.userId

        userIdRepository.validatedExists(userId)

        realNameRepository.add(
            userId = userId,
            realName = input.realName,
        )
    }

}

data class RealNameRegisterInputData(
    val userId: UUID,
    val realName: String,
)