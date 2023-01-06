package domein.model.car

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


private class CarTest {


    @Test
    fun `create method return instance as expect`() {
        val tire = Tire("tire")
        val actual = Car.create(tire)
        val expect = Car.create(tire)

        assertEquals(expect.tire,actual.tire)
    }

}