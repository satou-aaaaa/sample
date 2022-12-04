package presentation

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import java.util.*


@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.NONE,
)
private class SampleControllerTest {

    @Autowired
    lateinit var sut:SampleController

    @Test
    fun `test`() {
        val fetch = sut.fetchEmployee(UUID.randomUUID())
        println("fetch => $fetch")
    }
}