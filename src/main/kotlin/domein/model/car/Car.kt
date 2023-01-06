package domein.model.car

import java.util.*

class Car private constructor(
    val carId: UUID,
    val tire:Tire,
) {

    companion object {

        fun create(carId:UUID,tire: Tire): Car {
            return Car(carId,tire)
        }
    }
}