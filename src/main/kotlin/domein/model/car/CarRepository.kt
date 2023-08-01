package domein.model.car

import java.util.UUID

interface CarRepository {

    fun add(car: Car)

    fun resolveBy(carId: UUID): Car
}
