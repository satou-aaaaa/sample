package presentation

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import java.util.*

@Controller
class SampleController {

    @QueryMapping
    fun fetchEmployee(@Argument employeeId:UUID): EmployeeQueryDto {
        println("fetchEmployee coll")
        return EmployeeQueryDto(
            employeeId = UUID.fromString("750bdac6-7b62-4bd0-b2ce-d5648f87979e"),
            name = "satou",
        )
    }
}