package domein.model.car

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import java.util.*


private class CarTest {


    @Test
    fun `create method return instance as expect`() {
        val tire = Tire.create("tire")
        val actual = Car.create(
            tire = tire,
            )
        val expect = Car.create(
            tire = tire,
        )

        assertNotEquals(expect.carId,actual.carId)
        assertEquals(expect.tire,actual.tire)
    }

}