package kissloryshy.hotelbooking.reservationservice.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import kissloryshy.hotelbooking.reservationservice.entity.Client
import kissloryshy.hotelbooking.reservationservice.entity.Room
import kissloryshy.hotelbooking.reservationservice.entity.dto.ReservationDto
import kissloryshy.hotelbooking.reservationservice.repository.ReservationRepository
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.testcontainers.junit.jupiter.Testcontainers
import java.math.BigDecimal
import java.time.LocalDate

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores::class)
class ReservationControllerTestcontainers : AbstractIntegrationTest() {

    @Autowired
    lateinit var reservationRepository: ReservationRepository

    @Autowired
    lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setUp() {
        reservationRepository.count()
    }

    @Test
    @Order(1)
    fun create() {
        val date = LocalDate.now()

        val reservationDto = ReservationDto(
            Client(1, "kissloryshy", "lory", "kiss", "kissloryshy@gmail.com", "+79044488877", date.minusYears(20)),
            Room(1, 1, 2, 2, true, BigDecimal(2500), BigDecimal(2850)),
            date,
            date,
            date.plusDays(3)
        )

        val result = mockMvc.perform(
            MockMvcRequestBuilders
                .post("/api/reservations/create")
                .content(
                    ObjectMapper().registerModule(JavaTimeModule())
                        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writeValueAsString(reservationDto)
                )
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
        result
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.jsonPath("$.client.username").value("kissloryshy"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.room.capacity").value(2))
            .andExpect(MockMvcResultMatchers.jsonPath("$.reservationStart").value(date.toString()))

        println(result.andReturn().response.contentAsString)
    }

}
