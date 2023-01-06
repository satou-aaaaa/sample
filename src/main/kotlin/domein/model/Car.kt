package domein.model

data class Car(
    val tire:String,
) {

    companion object {

        fun create(tire: String): Car {
            return Car(tire)
        }
    }
}