package domein.model.car

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*

private class TireTest {


    @Test
    fun `create method return instance as expect`() {
        val carId = UUID.fromString("e23b42ae-4d56-4545-8c7d-857ec1dad0a1")
        val actual = Tire.create(carId,"tire")
        val expect = Tire.create(carId,"tire")

        assertEquals(expect.carId,actual.carId)
        assertNotEquals(expect.tireId,actual.tireId)
        assertEquals(expect.value,actual.value)
    }
}