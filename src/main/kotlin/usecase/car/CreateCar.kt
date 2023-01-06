package usecase.car

import domein.model.car.Car
import domein.model.car.CarRepository
import domein.model.car.Tire
import domein.model.car.TireRepository
import java.util.*

class CreateCar(
    private val tireRepository: TireRepository,
    private val carRepository: CarRepository,
) {

    fun handle(input: String) {
        val tire = Tire.create(input)
        tireRepository.add(tire)

        val car = Car.create(
            carId = UUID.randomUUID(),
            tire = tire,
        )
        carRepository.add(car)
    }
}