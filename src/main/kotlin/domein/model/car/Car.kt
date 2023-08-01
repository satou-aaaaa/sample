package domein.model.car

import java.util.UUID

class Car private constructor(
    val carId: UUID,
    val tire: Tire,
) {

    companion object {

        fun create(tire: Tire): Car {
            val carId = UUID.randomUUID()
            return Car(carId, tire)
        }
    }
}
