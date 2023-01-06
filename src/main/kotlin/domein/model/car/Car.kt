package domein.model.car

data class Car private constructor(
    private val tire:String,
) {

    companion object {

        fun create(tire: String): Car {
            return Car(tire)
        }
    }
}