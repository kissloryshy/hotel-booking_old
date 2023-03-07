package kissloryshy.hotelbooking.reservationservice.service

import kissloryshy.hotelbooking.reservationservice.entity.Client
import kissloryshy.hotelbooking.reservationservice.entity.Reservation
import kissloryshy.hotelbooking.reservationservice.entity.Room
import kissloryshy.hotelbooking.reservationservice.entity.dto.ReservationCountDto
import kissloryshy.hotelbooking.reservationservice.repository.ReservationRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.time.LocalDate

class ReservationServiceTest {
    @Mock
    private lateinit var reservationRepository: ReservationRepository

    @InjectMocks
    private lateinit var reservationService: ReservationService

    init {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun getCount() {
        val reservationCountDto = ReservationCountDto(10)
        `when`(reservationRepository.count()).thenReturn(reservationCountDto.reservationCount)

        val returnedCount = reservationService.getCount().reservationCount

        assertEquals(reservationCountDto.reservationCount, returnedCount)
    }

    @Test
    fun getAll() {
        val reservation1 = Reservation(Client(), Room(), LocalDate.now(), LocalDate.now(), LocalDate.now())
        val reservation2 = Reservation(Client(), Room(), LocalDate.now(), LocalDate.now(), LocalDate.now())
        val reservations = listOf(reservation1, reservation2)

        `when`(reservationRepository.findAll()).thenReturn(reservations)

        val returnedClients = reservationService.getAll()

        assertEquals(reservations, returnedClients)
    }
}