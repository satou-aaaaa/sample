package domein.model.car

import java.util.UUID

data class Tire(
    val tireId: UUID,
    val value: String
) {

    companion object {

        fun create(value: String): Tire {
            return Tire(
                tireId = UUID.randomUUID(),
                value = value
            )
        }
    }
}
