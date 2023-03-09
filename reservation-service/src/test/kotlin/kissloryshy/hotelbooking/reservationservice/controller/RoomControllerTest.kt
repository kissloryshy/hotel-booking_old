package kissloryshy.hotelbooking.reservationservice.controller

import kissloryshy.hotelbooking.reservationservice.entity.Room
import kissloryshy.hotelbooking.reservationservice.entity.dto.RoomCountDto
import kissloryshy.hotelbooking.reservationservice.exception.exceptions.RoomNotFoundException
import kissloryshy.hotelbooking.reservationservice.service.RoomService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.web.bind.MethodArgumentNotValidException
import java.math.BigDecimal

@ExtendWith(SpringExtension::class)
@WebMvcTest(RoomController::class)
class RoomControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var roomService: RoomService

    @Test
    fun getCount() {
        val room1 = Room(1, 1, 1, 1, true, BigDecimal(15), BigDecimal(30))
        val room2 = Room(1, 2, 3, 2, true, BigDecimal(20), BigDecimal(35))
        val rooms = listOf(room1, room2)
        val roomCountDto = RoomCountDto(rooms.size.toLong())

        `when`(roomService.getCount()).thenReturn(roomCountDto)

        val request =
            MockMvcRequestBuilders.get("/api/rooms/getCount").contentType(MediaType.APPLICATION_JSON)

        mockMvc.perform(request)
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.roomCount").value(roomCountDto.roomCount))

        Mockito.verify(roomService, Mockito.times(1)).getCount()
    }

    @Test
    fun getAll() {
        val room1 = Room(1, 1, 1, 1, true, BigDecimal(15), BigDecimal(30))
        val room2 = Room(1, 2, 3, 2, true, BigDecimal(20), BigDecimal(35))
        val rooms = listOf(room1, room2)
        val roomCount = rooms.size

        `when`(roomService.getAll()).thenReturn(rooms)

        val request = MockMvcRequestBuilders
            .get("/api/rooms/getAll")
            .contentType(MediaType.APPLICATION_JSON)

        mockMvc.perform(request)
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()").value(roomCount))
            .andExpect(jsonPath("$[0].number").value(rooms[0].number))
            .andExpect(jsonPath("$[1].number").value(rooms[1].number))

        Mockito.verify(roomService, Mockito.times(1)).getAll()
    }

    @Test
    fun getByNumber_exists() {
        val room1 = Room(1, 1, 1, 1, true, BigDecimal(15), BigDecimal(30))

        `when`(roomService.getByNumber(1)).thenReturn(room1)

        val id = 1
        val request =
            MockMvcRequestBuilders.get("/api/rooms/getByNumber/$id").contentType(MediaType.APPLICATION_JSON)

        mockMvc.perform(request)
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.number").value(1))

        Mockito.verify(roomService, Mockito.times(1)).getByNumber(id)
    }

    @Test
    fun getByNumber_notExists() {
        val roomNumber = "1000"
        val request =
            MockMvcRequestBuilders.get("/api/rooms/getByNumber/$roomNumber").contentType(MediaType.APPLICATION_JSON)

        mockMvc.perform(request)
            .andExpect(status().isNotFound)
            .andExpect { res -> assertTrue(res.resolvedException is RoomNotFoundException) }
            .andExpect { res ->
                assertEquals(
                    "Room not found with number: $roomNumber",
                    res.resolvedException!!.message
                )
            }
    }

    @Test
    fun getByNumber_argumentNotValid() {
        val roomNumber = "-1000"
        val request =
            MockMvcRequestBuilders.get("/api/rooms/getByNumber/$roomNumber").contentType(MediaType.APPLICATION_JSON)

        mockMvc.perform(request)
            .andExpect(status().isBadRequest)
            .andExpect { res -> assertTrue(res.resolvedException is MethodArgumentNotValidException) }
            .andExpect { res ->
                assertEquals(
                    "Room number cannot be negative. Received number: $roomNumber",
                    res.resolvedException!!.message
                )
            }
    }
}