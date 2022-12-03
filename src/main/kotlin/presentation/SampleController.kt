package presentation

import org.springframework.graphql.data.method.annotation.QueryMapping
import java.util.*

class SampleController {

    @QueryMapping
    fun fetchEmployee(): EmployeeDto {
        return EmployeeDto(
            employeeId = UUID.fromString("750bdac6-7b62-4bd0-b2ce-d5648f87979e"),
        )
    }
}