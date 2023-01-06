package domein.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


private class CarTest {

    @Test
    fun test() {
        val actual = Car("tire")
        val expect = Car("tire")

        assertEquals(expect,actual)
    }

    @Test
    fun `create method return instance as expect`() {
        val actual = Car.create("tire")
        val expect = Car.create("tire")

        assertEquals(expect,actual)
    }
}