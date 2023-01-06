package domein.model.car

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

private class TireTest {

    @Test
    fun test() {
        val actual = Tire("tire")
        val expect = Tire("tire")

        assertEquals(expect,actual)
    }

    @Test
    fun `create method return instance as expect`() {
        val actual = Tire.create("tire")
        val expect = Tire.create("tire")

        assertEquals(expect.tireId,actual.tireId)
        assertEquals(expect.value,actual.value)
    }
}