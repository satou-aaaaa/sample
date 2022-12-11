package usecase

import domein.model.user.UserRealNameRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class DeleteRealName(
    private val userRealNameRepository: UserRealNameRepository
) {

    fun handle(input: RealNameRemoveInputData) {
        userRealNameRepository.remove(input.userId)
    }
}

data class RealNameRemoveInputData(
    val userId: UUID,
)