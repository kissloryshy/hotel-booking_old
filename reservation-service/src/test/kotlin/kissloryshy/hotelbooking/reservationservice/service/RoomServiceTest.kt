package kissloryshy.hotelbooking.reservationservice.service

import kissloryshy.hotelbooking.reservationservice.entity.Room
import kissloryshy.hotelbooking.reservationservice.entity.dto.RoomCountDto
import kissloryshy.hotelbooking.reservationservice.repository.RoomRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import java.math.BigDecimal

@ExtendWith(MockitoExtension::class)
class RoomServiceTest {
    @Mock
    private lateinit var roomRepository: RoomRepository

    @InjectMocks
    private lateinit var roomService: RoomService

    @Test
    fun getCount() {
        val roomCountDto = RoomCountDto(10)
        `when`(roomRepository.count()).thenReturn(roomCountDto.roomCount)

        val returnedCount = roomService.getCount().roomCount

        assertEquals(roomCountDto.roomCount, returnedCount)
    }

    @Test
    fun getAll() {
        val room1 = Room(1, 1, 1, 1, true, BigDecimal(15), BigDecimal(30))
        val room2 = Room(2, 2, 3, 2, true, BigDecimal(20), BigDecimal(35))
        val rooms = listOf(room1, room2)

        `when`(roomRepository.findAll()).thenReturn(rooms)

        val returnedRooms = roomService.getAll()

        assertEquals(rooms, returnedRooms)
    }

    @Test
    fun getByNumber_exists() {
        val room = Room(1, 1, 1, 1, true, BigDecimal(15), BigDecimal(30))
        val roomNumber = 1

        `when`(roomRepository.findByNumber(roomNumber)).thenReturn(room)

        val returnedRoom = roomService.getByNumber(roomNumber)

        if (returnedRoom != null) {
            assertEquals(roomNumber, returnedRoom.number)
        }
    }

    @Test
    fun create() {
        val roomNum = 7
        val roomCap = 2
        val room = Room(1, 7, roomCap, 2, true, BigDecimal(10000), BigDecimal(16000))

        `when`(roomRepository.save(room)).thenReturn(room)

        val returnedRoom = roomRepository.save(room)

        assertEquals(roomNum, returnedRoom.number)
        assertEquals(roomCap, returnedRoom.capacity)
    }
}
