package kissloryshy.hotelbooking.reservationservice.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import kissloryshy.hotelbooking.reservationservice.entity.Client
import kissloryshy.hotelbooking.reservationservice.entity.Room
import kissloryshy.hotelbooking.reservationservice.entity.dto.ClientDto
import kissloryshy.hotelbooking.reservationservice.entity.dto.ReservationCountDto
import kissloryshy.hotelbooking.reservationservice.entity.dto.ReservationDto
import kissloryshy.hotelbooking.reservationservice.entity.dto.RoomDto
import kissloryshy.hotelbooking.reservationservice.mapper.ClientMapper
import kissloryshy.hotelbooking.reservationservice.mapper.RoomMapper
import kissloryshy.hotelbooking.reservationservice.service.ReservationService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mapstruct.factory.Mappers
import org.mockito.BDDMockito.given
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
import java.math.BigDecimal
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
        val reservation1 = ReservationDto(Client(), Room(), LocalDate.now(), LocalDate.now(), LocalDate.now())
        val reservation2 = ReservationDto(Client(), Room(), LocalDate.now(), LocalDate.now(), LocalDate.now())
        val reservations = listOf(reservation1, reservation2)
        val reservationCountDto = ReservationCountDto(reservations.size.toLong())

        `when`(reservationService.getCount()).thenReturn(reservationCountDto)

        val request = MockMvcRequestBuilders
            .get("/api/reservations/getCount")
            .contentType(MediaType.APPLICATION_JSON)

        mockMvc.perform(request)
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.reservationCount").value(reservationCountDto.reservationCount))

        Mockito.verify(reservationService, times(1)).getCount()
    }

    @Test
    fun getAll() {
        val reservation1 = ReservationDto(Client(), Room(), LocalDate.now(), LocalDate.now(), LocalDate.now())
        val reservation2 = ReservationDto(Client(), Room(), LocalDate.now(), LocalDate.now(), LocalDate.now())
        val reservations = listOf(reservation1, reservation2)

        `when`(reservationService.getAll()).thenReturn(reservations)

        val request = MockMvcRequestBuilders
            .get("/api/reservations/getAll")
            .contentType(MediaType.APPLICATION_JSON)

        mockMvc.perform(request)
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()").value(reservations.size))

        Mockito.verify(reservationService, times(1)).getAll()
    }

    @Test
    fun getByClientUsername() {
        val testUn = "holla-amigo"
        val clientDto = ClientDto(testUn, "testFn", "testLn", "test@mail.com", "+79000000000", LocalDate.now())
        val converter = Mappers.getMapper(ClientMapper::class.java)
        val reservationDto =
            ReservationDto(converter.toModel(clientDto), Room(), LocalDate.now(), LocalDate.now(), LocalDate.now())
        val reservations = listOf(reservationDto)

        `when`(reservationService.getByClientUsername(testUn)).thenReturn(reservations)

        val request = MockMvcRequestBuilders
            .get("/api/reservations/getByClientUsername/$testUn")
            .contentType(MediaType.APPLICATION_JSON)

        mockMvc.perform(request)
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()").value(reservations.size))
            .andExpect(jsonPath("$[0].client.username").value(testUn))

        Mockito.verify(reservationService, times(1)).getByClientUsername(testUn)
    }

    @Test
    fun getByRoomNumber() {
        val testNumber = 7
        val roomDto = RoomDto(testNumber, 2, 2, true, BigDecimal(2000), BigDecimal(2800))
        val converter = Mappers.getMapper(RoomMapper::class.java)
        val reservationDto =
            ReservationDto(Client(), converter.toModel(roomDto), LocalDate.now(), LocalDate.now(), LocalDate.now())
        val reservations = listOf(reservationDto)

        `when`(reservationService.getByRoomNumber(testNumber)).thenReturn(reservations)

        val request = MockMvcRequestBuilders
            .get("/api/reservations/getByRoomNumber/$testNumber")
            .contentType(MediaType.APPLICATION_JSON)

        mockMvc.perform(request)
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()").value(reservations.size))
            .andExpect(jsonPath("$[0].room.number").value(testNumber))

        Mockito.verify(reservationService, times(1)).getByRoomNumber(testNumber)
    }

    @Test
    fun create() {
        val date = LocalDate.now()
        val clientDto = ClientDto("testUn", "testFn", "testLn", "test@mail.com", "+79000000000", LocalDate.now())
        val roomDto = RoomDto(77, 2, 2, true, BigDecimal(2000), BigDecimal(2800))

        val reservationDto = ReservationDto(
            Mappers.getMapper(ClientMapper::class.java).toModel(clientDto),
            Mappers.getMapper(RoomMapper::class.java).toModel(roomDto),
            date,
            LocalDate.now(),
            LocalDate.now()
        )

        `when`(reservationService.create(reservationDto)).thenReturn(reservationDto)

        val objectMapper = ObjectMapper()
        objectMapper.registerModule(JavaTimeModule())
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        val reservationDtoJson: String = objectMapper.writeValueAsString(reservationDto)

        val result = mockMvc.perform(
            MockMvcRequestBuilders.post("/api/reservations/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(reservationDtoJson)
                .accept(MediaType.APPLICATION_JSON)
        )

        result
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.client.username").value("testUn"))
            .andExpect(jsonPath("$.room.capacity").value(2))
            .andExpect(jsonPath("$.reservationStart").value(date.toString()))

        Mockito.verify(reservationService, times(1)).create(reservationDto)
    }

}
