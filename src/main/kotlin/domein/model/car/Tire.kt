package domein.model.car

import java.util.UUID

data class Tire(
    val carId:UUID,
    val tireId:UUID,
    val value:String,
){

    companion object {

        fun create(carId:UUID,value: String): Tire {
            return Tire(
                carId = carId,
                tireId = UUID.randomUUID(),
                value = value,
                )
        }
    }
}