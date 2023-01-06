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
}