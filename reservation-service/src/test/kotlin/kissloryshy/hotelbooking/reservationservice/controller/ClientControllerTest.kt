package kissloryshy.hotelbooking.reservationservice.controller

import kissloryshy.hotelbooking.reservationservice.entity.Client
import kissloryshy.hotelbooking.reservationservice.entity.dto.ClientCountDto
import kissloryshy.hotelbooking.reservationservice.exception.exceptions.ClientNotFoundException
import kissloryshy.hotelbooking.reservationservice.service.ClientService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.*
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.LocalDate


@ExtendWith(SpringExtension::class)
@WebMvcTest(ClientController::class)
class ClientControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var messageKafkaTemplate: KafkaTemplate<String, String>

    @MockBean
    private lateinit var clientKafkaTemplate: KafkaTemplate<String, Client>

    @MockBean
    private lateinit var clientService: ClientService

    @Test
    fun getCount() {
        val client1 = Client("testUn1", "testFn1", "testLn1", "testEm1", "testPn1", LocalDate.now(), mutableSetOf())
        val client2 = Client("testUn2", "testFn2", "testLn2", "testEm2", "testPn2", LocalDate.now(), mutableSetOf())
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
        val client1 = Client("testUn1", "testFn1", "testLn1", "testEm1", "testPn1", LocalDate.now(), mutableSetOf())
        val client2 = Client("testUn2", "testFn2", "testLn2", "testEm2", "testPn2", LocalDate.now(), mutableSetOf())
        val clients = listOf(client1, client2)

        `when`(clientService.getAll()).thenReturn(clients)

        val request =
            MockMvcRequestBuilders.get("/api/clients/getAll").contentType(MediaType.APPLICATION_JSON)
        val result = mockMvc.perform(request)

        result
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()").value(clients.size))
            .andExpect(jsonPath("$[0].firstName").value(clients[0].firstName))
            .andExpect(jsonPath("$[1].phoneNumber").value(clients[1].phoneNumber))

        Mockito.verify(clientService, times(1)).getAll()
    }

    @Test
    fun getByUsername_exists() {
        val un = "username"
        val client = Client(un, "firstName", "lastName", "email", "phoneNumber", LocalDate.now(), mutableSetOf())

        `when`(clientService.getByUsername("username")).thenReturn(client)

        val request =
            MockMvcRequestBuilders.get("/api/clients/getByUsername/$un").contentType(MediaType.APPLICATION_JSON)
        val result = mockMvc.perform(request)

        result
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.firstName").value("firstName"))
            .andExpect(jsonPath("$.email").value("email"))

        Mockito.verify(clientService, times(1)).getByUsername(un)
    }

    @Test
    fun getByUsername_notExists() {
        val username = "ff2158bb-800a-4c1b-b0dd-4d2b55b9f3c0"
        val request =
            MockMvcRequestBuilders.get("/api/clients/getByUsername/$username").contentType(MediaType.APPLICATION_JSON)
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
}
