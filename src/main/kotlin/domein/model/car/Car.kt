package domein.model.car

class Car private constructor(
    val tire:Tire,
) {

    companion object {

        fun create(tire: Tire): Car {
            return Car(tire)
        }
    }
}