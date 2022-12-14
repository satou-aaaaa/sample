package domein.model.car

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*

private class TireTest {


    @Test
    fun `create method return instance as expect`() {
        val actual = Tire.create("tire")
        val expect = Tire.create("tire")

        assertNotEquals(expect.tireId,actual.tireId)
        assertEquals(expect.value,actual.value)
    }
}