package kissloryshy.hotelbooking.reservationservice.service

import kissloryshy.hotelbooking.reservationservice.entity.Client
import kissloryshy.hotelbooking.reservationservice.entity.Reservation
import kissloryshy.hotelbooking.reservationservice.entity.Room
import kissloryshy.hotelbooking.reservationservice.entity.dto.ReservationCountDto
import kissloryshy.hotelbooking.reservationservice.mapper.ReservationMapper
import kissloryshy.hotelbooking.reservationservice.repository.ReservationRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mapstruct.factory.Mappers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import java.math.BigDecimal
import java.time.LocalDate

@ExtendWith(MockitoExtension::class)
class ReservationServiceTest {
    @Mock
    private lateinit var reservationRepository: ReservationRepository

    @InjectMocks
    private lateinit var reservationService: ReservationService

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

        val mapper = Mappers.getMapper(ReservationMapper::class.java)
        assertTrue(reservations[0].reservationEnd == returnedClients.map { mapper.toModel(it) }[0].reservationEnd)
        assertTrue(reservations[0].reservationEnd == returnedClients.map { mapper.toModel(it) }[0].reservationEnd)
    }

    @Test
    fun getByClientUsername() {
        val username = "kiss"
        val roomCap = 2
        val client = Client(
            1,
            username,
            "firstName",
            "lastName",
            "kissloryshy@gmail.com",
            "+79044455677",
            LocalDate.now().minusYears(25)
        )
        val room = Room(1, 7, roomCap, 2, true, BigDecimal(10000), BigDecimal(16000))
        val reservation = Reservation(client, room, LocalDate.now(), LocalDate.now(), LocalDate.now().plusWeeks(1))
        val reservations = mutableListOf(reservation)

        `when`(reservationRepository.findByClient_Username(username)).thenReturn(reservations)

        val returnedReservation = reservationService.getByClientUsername(username)

        assertEquals(username, returnedReservation[0].client.username)
        assertEquals(roomCap, returnedReservation[0].room.capacity)
    }

    @Test
    fun getByRoomNumber() {
        val roomNum = 7
        val username = "kiss"
        val roomCap = 2
        val client = Client(
            1,
            username,
            "firstName",
            "lastName",
            "kissloryshy@gmail.com",
            "+79044455677",
            LocalDate.now().minusYears(25)
        )
        val room = Room(1, 7, roomCap, 2, true, BigDecimal(10000), BigDecimal(16000))
        val reservation = Reservation(client, room, LocalDate.now(), LocalDate.now(), LocalDate.now().plusWeeks(1))
        val reservations = mutableListOf(reservation)

        `when`(reservationRepository.findByRoom_Number(roomNum)).thenReturn(reservations)

        val returnedReservation = reservationService.getByRoomNumber(roomNum)

        assertEquals(username, returnedReservation[0].client.username)
        assertEquals(roomCap, returnedReservation[0].room.capacity)
    }

    @Test
    fun create() {
        val username = "kiss"
        val roomNum = 7
        val roomCap = 2
        val client = Client(
            1,
            username,
            "firstName",
            "lastName",
            "kissloryshy@gmail.com",
            "+79044455677",
            LocalDate.now().minusYears(25)
        )
        val room = Room(1, 7, roomCap, 2, true, BigDecimal(10000), BigDecimal(16000))
        val reservation = Reservation(client, room, LocalDate.now(), LocalDate.now(), LocalDate.now().plusWeeks(1))

        `when`(reservationRepository.save(reservation)).thenReturn(reservation)

        val returnedReservation = reservationRepository.save(reservation)

        assertEquals(username, returnedReservation.client?.username)
        assertEquals(roomNum, returnedReservation.room?.number)
        assertEquals(roomCap, returnedReservation.room?.capacity)
    }

}
