package domein.model.car

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*


private class CarTest {


    @Test
    fun `create method return instance as expect`() {
        val carId = UUID.fromString("52adab23-ef19-49c7-aad8-46fa2ad6d84e")
        val tire = Tire.create(carId,"tire")
        val actual = Car.create(
            carId = carId,
            tire = tire,
            )
        val expect = Car.create(
            carId = carId,
            tire = tire,
        )

        assertEquals(expect.carId,actual.carId)
        assertEquals(expect.tire,actual.tire)
    }

}