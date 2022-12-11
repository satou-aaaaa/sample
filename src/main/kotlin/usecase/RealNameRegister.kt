package usecase

import domein.model.user.UserIdRepository
import domein.model.user.UserRealNameRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class RealNameRegister(
    private val userIdRepository: UserIdRepository,
    private val realNameRepository: UserRealNameRepository,
) {

    fun handle(input: RealNameRegisterInputData) {
        val userId = input.userId

        userIdRepository.validatedIdExists(userId)

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