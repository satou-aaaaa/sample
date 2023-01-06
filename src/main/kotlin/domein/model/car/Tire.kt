package domein.model.car

data class Tire(val value:String){

    companion object {

        fun create(value: String): Tire {
            return Tire(value)
        }
    }
}