package usecase

import domein.model.user.UserRealNameRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class UpdateRealName(
    private val userRealNameRepository: UserRealNameRepository
) {

    fun handle(input: RealNameUpdateInputData) {

        userRealNameRepository.replace(
            userId = input.userId,
            realName = input.realName,
        )
    }
}

data class RealNameUpdateInputData(
    val userId: UUID,
    val realName: String
)