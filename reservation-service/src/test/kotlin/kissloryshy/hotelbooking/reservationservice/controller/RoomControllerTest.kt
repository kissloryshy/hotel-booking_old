package kissloryshy.hotelbooking.reservationservice.controller

import kissloryshy.hotelbooking.reservationservice.entity.Room
import kissloryshy.hotelbooking.reservationservice.entity.dto.RoomCountDto
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
        val room1 = Room(1, 1, 1, true, BigDecimal(15), BigDecimal(30), mutableSetOf())
        val room2 = Room(2, 3, 2, true, BigDecimal(20), BigDecimal(35), mutableSetOf())
        val rooms = listOf(room1, room2)
        val roomCountDto = RoomCountDto(rooms.size.toLong())

        `when`(roomService.getCount()).thenReturn(roomCountDto)

        val request =
            MockMvcRequestBuilders.get("/api/rooms/getCount").contentType(MediaType.APPLICATION_JSON)
        val result = mockMvc.perform(request)

        result
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.roomCount").value(roomCountDto.roomCount))

        Mockito.verify(roomService, Mockito.times(1)).getCount()
    }

    @Test
    fun getAll() {
        val room1 = Room(1, 1, 1, true, BigDecimal(15), BigDecimal(30), mutableSetOf())
        val room2 = Room(2, 3, 2, true, BigDecimal(20), BigDecimal(35), mutableSetOf())
        val rooms = listOf(room1, room2)
        val roomCount = rooms.size

        `when`(roomService.getAll()).thenReturn(rooms)

        val request = MockMvcRequestBuilders.get("/api/rooms/getAll").contentType(MediaType.APPLICATION_JSON)
        val result = mockMvc.perform(request)

        result
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()").value(roomCount))
            .andExpect(jsonPath("$[0].roomNumber").value(rooms[0].roomNumber))
            .andExpect(jsonPath("$[1].roomNumber").value(rooms[1].roomNumber))

        Mockito.verify(roomService, Mockito.times(1)).getAll()
    }

    @Test
    fun getByRoomNumber() {
        val room1 = Room(1, 1, 1, true, BigDecimal(15), BigDecimal(30), mutableSetOf())

//        TODO
//        `when`(roomService.getByRoomNumber(1)).thenReturn(room1)

        val id = 1
        val request =
            MockMvcRequestBuilders.get("/api/rooms/getByRoomNumber/$id").contentType(MediaType.APPLICATION_JSON)
        val result = mockMvc.perform(request)

        result
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.roomNumber").value(1))

        Mockito.verify(roomService, Mockito.times(1)).getByRoomNumber(id.toLong())
    }
}