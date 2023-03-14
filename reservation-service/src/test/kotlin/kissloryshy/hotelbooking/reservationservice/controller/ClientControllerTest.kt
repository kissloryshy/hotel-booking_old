package kissloryshy.hotelbooking.reservationservice.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import kissloryshy.hotelbooking.reservationservice.config.KafkaProducerTestConfig
import kissloryshy.hotelbooking.reservationservice.entity.dto.ClientCountDto
import kissloryshy.hotelbooking.reservationservice.entity.dto.ClientDto
import kissloryshy.hotelbooking.reservationservice.exception.exceptions.ClientNotFoundException
import kissloryshy.hotelbooking.reservationservice.service.ClientService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.*
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.LocalDate

@ExtendWith(SpringExtension::class)
@WebMvcTest(ClientController::class)
@Import(KafkaProducerTestConfig::class)
class ClientControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    @Qualifier("messageKafkaTemplate")
    private lateinit var messageKafkaTemplate: KafkaTemplate<String, String>

    @MockBean
    @Qualifier("clientDtoKafkaTemplate")
    private lateinit var clientKafkaTemplate: KafkaTemplate<String, ClientDto>

    @MockBean
    private lateinit var clientService: ClientService

    @Test
    fun getCount() {
        val client1 =
            ClientDto("testUn1", "testFn1", "testLn1", "kissloryshy1@gmail.com", "+79044477899", LocalDate.now())
        val client2 =
            ClientDto("testUn2", "testFn2", "testLn2", "kissloryshy2@gmail.com", "+79044477899", LocalDate.now())
        val clients = listOf(client1, client2)
        val clientCountDto = ClientCountDto(clients.size.toLong())

        `when`(clientService.getCount()).thenReturn(clientCountDto)

        val request =
            MockMvcRequestBuilders.get("/api/clients/getCount").contentType(MediaType.APPLICATION_JSON)
        val result = mockMvc.perform(request)

        result
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.clientCount").value(clientCountDto.clientCount))

        Mockito.verify(clientService, times(1)).getCount()
    }

    @Test
    fun getAll() {
        val client1 =
            ClientDto("testUn1", "testFn1", "testLn1", "kissloryshy1@gmail.com", "+79044477899", LocalDate.now())
        val client2 =
            ClientDto("testUn2", "testFn2", "testLn2", "kissloryshy2@gmail.com", "+79044477899", LocalDate.now())
        val clients = listOf(client1, client2)

        `when`(clientService.getAll(0, 5)).thenReturn(clients)

        val request = MockMvcRequestBuilders
            .get("/api/clients/getAll/0/5")
            .contentType(MediaType.APPLICATION_JSON)

        mockMvc.perform(request)
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()").value(clients.size))
            .andExpect(jsonPath("$[0].firstName").value(clients[0].firstName))
            .andExpect(jsonPath("$[1].phoneNumber").value(clients[1].phoneNumber))

        Mockito.verify(clientService, times(1)).getAll(0, 5)
    }

    @Test
    fun getByUsername_exists() {
        val un = "username"
        val client = ClientDto(un, "testFn1", "testLn1", "kissloryshy1@gmail.com", "+79044477899", LocalDate.now())

        `when`(clientService.getByUsername("username")).thenReturn(client)

        val request =
            MockMvcRequestBuilders
                .get("/api/clients/getByUsername/$un")
                .contentType(MediaType.APPLICATION_JSON)

        mockMvc.perform(request)
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.firstName").value("testFn1"))
            .andExpect(jsonPath("$.email").value("kissloryshy1@gmail.com"))

        Mockito.verify(clientService, times(1)).getByUsername(un)
    }

    @Test
    fun getByUsername_notExists() {
        val username = "ff2158bb-800a-4c1b-b0dd-4d2b55b9f3c0"
        val request =
            MockMvcRequestBuilders
                .get("/api/clients/getByUsername/$username")
                .contentType(MediaType.APPLICATION_JSON)

        mockMvc.perform(request)
            .andExpect(status().isNotFound)
            .andExpect { res -> assertTrue(res.resolvedException is ClientNotFoundException) }
            .andExpect { res ->
                assertEquals(
                    "Client not found with username: $username",
                    res.resolvedException!!.message
                )
            }
    }

    @Test
    fun getByUsername_argumentNotValid() {
        val username = "-"
        val request =
            MockMvcRequestBuilders
                .get("/api/clients/getByUsername/$username")
                .contentType(MediaType.APPLICATION_JSON)
        val result = mockMvc.perform(request)
        result
            .andExpect(status().isNotFound)
            .andExpect { res -> assertTrue(res.resolvedException is ClientNotFoundException) }
            .andExpect { res ->
                assertEquals(
                    "Client not found with username: $username",
                    res.resolvedException!!.message
                )
            }
    }

    @Test
    fun create() {
        val date = LocalDate.now()
        val clientDto = ClientDto("username", "testFn1", "testLn1", "kissloryshy1@gmail.com", "+79044477899", date)

        `when`(clientService.create(clientDto)).thenReturn(clientDto)

        val objectMapper = ObjectMapper()
        objectMapper.registerModule(JavaTimeModule())
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        val clientDtoJson: String = objectMapper.writeValueAsString(clientDto)

        val result = mockMvc.perform(
            MockMvcRequestBuilders.post("/api/clients/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(clientDtoJson)
                .accept(MediaType.APPLICATION_JSON)
        )

        result
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.username").value("username"))
            .andExpect(jsonPath("$.birthdate").value(date.toString()))

        verify(clientService, times(1)).create(clientDto)
    }

}
