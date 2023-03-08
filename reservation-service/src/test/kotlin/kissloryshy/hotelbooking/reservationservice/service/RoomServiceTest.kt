package kissloryshy.hotelbooking.reservationservice.service

import kissloryshy.hotelbooking.reservationservice.entity.Room
import kissloryshy.hotelbooking.reservationservice.entity.dto.RoomCountDto
import kissloryshy.hotelbooking.reservationservice.repository.RoomRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.math.BigDecimal

class RoomServiceTest {
    @Mock
    private lateinit var roomRepository: RoomRepository

    @InjectMocks
    private lateinit var roomService: RoomService

    init {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun getCount() {
        val roomCountDto = RoomCountDto(10)
        `when`(roomRepository.count()).thenReturn(roomCountDto.roomCount)

        val returnedCount = roomService.getCount().roomCount

        assertEquals(roomCountDto.roomCount, returnedCount)
    }

    @Test
    fun getAll() {
        val room1 = Room(1, 1, 1, true, BigDecimal(15), BigDecimal(30))
        val room2 = Room(2, 3, 2, true, BigDecimal(20), BigDecimal(35))
        val rooms = listOf(room1, room2)

        `when`(roomRepository.findAll()).thenReturn(rooms)

        val returnedRooms = roomService.getAll()

        assertEquals(rooms, returnedRooms)
    }

    @Test
    fun getByNumber_exists() {
        val room = Room(1, 1, 1, true, BigDecimal(15), BigDecimal(30))
        val roomNumber = 1

        `when`(roomRepository.findByNumber(roomNumber)).thenReturn(room)

        val returnedRoom = roomService.getByNumber(roomNumber)

        if (returnedRoom != null) {
            assertEquals(roomNumber, returnedRoom.number)
        }
    }
}