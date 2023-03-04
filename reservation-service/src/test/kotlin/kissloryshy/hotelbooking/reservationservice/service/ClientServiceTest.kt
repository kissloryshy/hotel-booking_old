package kissloryshy.hotelbooking.reservationservice.service

import kissloryshy.hotelbooking.reservationservice.entity.Client
import kissloryshy.hotelbooking.reservationservice.entity.dto.ClientCountDto
import kissloryshy.hotelbooking.reservationservice.repository.ClientRepository
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.time.LocalDate

class ClientServiceTest {
    @Mock
    private lateinit var clientRepository: ClientRepository
    @InjectMocks
    private lateinit var clientService: ClientService
    init {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun getCount() {
        val clientCountDto = ClientCountDto(10)
        `when`(clientRepository.count()).thenReturn(clientCountDto.clientCount)

        val returnedCount = clientService.getCount().clientCount

        assertEquals(clientCountDto.clientCount, returnedCount)
    }

    @Test
    fun getAll() {
        val client1 = Client("testUn1", "testFn1", "testLn1", "testEm1", "testPn1", LocalDate.now(), mutableSetOf())
        val client2 = Client("testUn2", "testFn2", "testLn2", "testEm2", "testPn2", LocalDate.now(), mutableSetOf())
        val clients = listOf(client1, client2)

        `when`(clientRepository.findAll()).thenReturn(clients)

        val returnedClients = clientService.getAll()

        assertEquals(clients, returnedClients)
    }

    @Test
    fun getByUsername() {
        val client = Client("testUn1", "testFn1", "testLn1", "testEm1", "testPn1", LocalDate.now(), mutableSetOf())
        val username = "testUn1"

        `when`(clientRepository.findClientByUsername(username)).thenReturn(client)

        val returnedClient = clientService.getByUsername(username)

        assertEquals(client, returnedClient)
    }
}