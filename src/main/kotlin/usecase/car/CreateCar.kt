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

    fun handle(input: CreateCarInputData) {
        val carId = UUID.randomUUID()
        val tire = Tire.create(carId,input.tire)
        tireRepository.add(tire)

        val car = Car.create(
            carId = carId,
            tire = tire,
        )
        carRepository.add(car)
    }
}

data class CreateCarInputData(
    val tire:String,
)