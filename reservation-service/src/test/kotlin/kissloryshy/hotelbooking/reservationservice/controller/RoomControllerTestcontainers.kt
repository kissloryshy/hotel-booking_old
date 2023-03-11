package kissloryshy.hotelbooking.reservationservice.controller

import com.fasterxml.jackson.databind.ObjectMapper
import kissloryshy.hotelbooking.reservationservice.entity.dto.RoomDto
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.math.BigDecimal

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@RunWith(SpringRunner::class)
@Testcontainers
class RoomControllerTestcontainers {
    @Autowired
    lateinit var mockMvc: MockMvc

    @LocalServerPort
    final val portNumber = 0

    val baseUrl = "http://localhost:$portNumber"

    companion object {
        @Container
        var postgreSQL: PostgreSQLContainer<*> = PostgreSQLContainer<Nothing>("postgres:15.2")

        @DynamicPropertySource
        fun postgreSQLProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.driver-class-name") { postgreSQL.driverClassName }
            registry.add("spring.datasource.username") { postgreSQL.username }
            registry.add("spring.datasource.password") { postgreSQL.password }
        }
    }

    @Test
    fun create() {
        val room = RoomDto(7, 2, 2, true, BigDecimal(3100), BigDecimal(3750))

        val result = mockMvc.perform(
            MockMvcRequestBuilders
                .post("$baseUrl$portNumber/api/rooms/create")
                .content(ObjectMapper().writeValueAsString(room))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
        result
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.jsonPath("$.number").value(7))
            .andExpect(MockMvcResultMatchers.jsonPath("$.capacity").value(2))

        println(result.andReturn().response.contentAsString)
    }
}
