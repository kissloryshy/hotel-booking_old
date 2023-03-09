package kissloryshy.hotelbooking.reservationservice.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.github.dockerjava.api.command.CreateContainerCmd
import com.github.dockerjava.api.model.ExposedPort
import com.github.dockerjava.api.model.HostConfig
import com.github.dockerjava.api.model.PortBinding
import com.github.dockerjava.api.model.Ports
import kissloryshy.hotelbooking.reservationservice.entity.Client
import kissloryshy.hotelbooking.reservationservice.entity.Room
import kissloryshy.hotelbooking.reservationservice.entity.dto.ReservationDto
import org.flywaydb.core.Flyway
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.PropertySource
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.math.BigDecimal
import java.time.LocalDate
import java.util.function.Consumer

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@PropertySource("classpath:application-test.yml")
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReservationControllerTestcontainers {

    lateinit var mockMvc: MockMvc

    companion object {
        private const val localPort = 10010
        private const val containerPort = 5432

        @Container
        val postgres: PostgreSQLContainer<*> = PostgreSQLContainer<Nothing>("postgres:15.2")
            .apply {
                withDatabaseName("hotel-booking")
                withUsername("postgres")
                withPassword("1212")
                withExposedPorts(containerPort)
                withCommand("-p $containerPort")
                withCreateContainerCmdModifier { cmd ->
                    cmd.withHostConfig(
                        HostConfig().withPortBindings(
                            PortBinding(Ports.Binding.bindPort(localPort), ExposedPort(containerPort))
                        )
                    )
                }
            }

        @BeforeAll
        @JvmStatic
        fun setUp() {
            postgres.start()
            val flyway = Flyway.configure()
                .dataSource(postgres.jdbcUrl, postgres.username, postgres.password)
                .load()
            flyway.migrate()
        }

        @AfterAll
        @JvmStatic
        fun tearDown() {
            postgres.stop()
        }

    }

    @Value("\${server.port}")
    var serverPort: Int? = null

    @BeforeAll
    fun setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(ReservationController::class).build()
    }

    @Test
    fun create() {
        val date = LocalDate.now()

        val reservationDto = ReservationDto(
            Client(1, "kissloryshy", "lory", "kiss", "kissloryshy@gmail.com", "+79044488877", date.minusYears(20)),
            Room(1, 1, 2, 2, true, BigDecimal(2500), BigDecimal(2850)),
            date,
            date,
            date.plusDays(3)
        )

        mockMvc.perform(
            MockMvcRequestBuilders
                .post("/api/reservations/create")
                .content(
                    ObjectMapper().registerModule(JavaTimeModule())
                        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writeValueAsString(reservationDto)
                )
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.jsonPath("$.client.username").value("kissloryshy"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.room.capacity").value(2))
            .andExpect(MockMvcResultMatchers.jsonPath("$.reservationStart").value(date.toString()))
    }

}
