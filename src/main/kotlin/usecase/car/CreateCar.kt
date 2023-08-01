package usecase.car

import domein.model.car.Car
import domein.model.car.CarRepository
import domein.model.car.Tire
import org.springframework.stereotype.Service

@Service
class CreateCar(
    private val carRepository: CarRepository,
) {

    fun handle(input: CreateCarInputData) {
        val tire = Tire.create(input.tire)

        val car = Car.create(
            tire = tire,
        )
        carRepository.add(car)
    }
}

data class CreateCarInputData(
    val tire: String,
)
