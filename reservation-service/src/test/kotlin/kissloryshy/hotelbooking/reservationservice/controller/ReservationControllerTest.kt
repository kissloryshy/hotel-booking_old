package kissloryshy.hotelbooking.reservationservice.controller

import kissloryshy.hotelbooking.reservationservice.entity.Client
import kissloryshy.hotelbooking.reservationservice.entity.Reservation
import kissloryshy.hotelbooking.reservationservice.entity.Room
import kissloryshy.hotelbooking.reservationservice.entity.dto.ReservationCountDto
import kissloryshy.hotelbooking.reservationservice.service.ReservationService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.LocalDate

@ExtendWith(SpringExtension::class)
@WebMvcTest(ReservationController::class)
class ReservationControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var reservationService: ReservationService

    @Test
    fun getCount() {
        val reservation1 = Reservation(Client(), Room(), LocalDate.now(), LocalDate.now(), LocalDate.now())
        val reservation2 = Reservation(Client(), Room(), LocalDate.now(), LocalDate.now(), LocalDate.now())
        val reservations = listOf(reservation1, reservation2)
        val reservationCountDto = ReservationCountDto(reservations.size.toLong())

        `when`(reservationService.getCount()).thenReturn(reservationCountDto)

        val request =
            MockMvcRequestBuilders.get("/api/reservations/getCount").contentType(MediaType.APPLICATION_JSON)
        val result = mockMvc.perform(request)

        result
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.reservationCount").value(reservationCountDto.reservationCount))

        Mockito.verify(reservationService, times(1)).getCount()
    }

    @Test
    fun getAll() {
        val testSignedDate = LocalDate.now()
        val reservation1 = Reservation(Client(), Room(), testSignedDate, LocalDate.now(), LocalDate.now())
        val reservation2 = Reservation(Client(), Room(), testSignedDate, LocalDate.now(), LocalDate.now())
        val reservations = listOf(reservation1, reservation2)

        `when`(reservationService.getAll()).thenReturn(reservations)

        val request =
            MockMvcRequestBuilders.get("/api/reservations/getAll").contentType(MediaType.APPLICATION_JSON)
        val result = mockMvc.perform(request)

        result
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()").value(reservations.size))

        Mockito.verify(reservationService, times(1)).getAll()
    }
}