package domein.model.car

import java.util.UUID

interface TireRepository {

    fun add(tire: Tire)

    fun resolveBy(tireId:UUID):Tire
}