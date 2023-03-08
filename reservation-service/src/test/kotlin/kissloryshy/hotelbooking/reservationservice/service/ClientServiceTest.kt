package kissloryshy.hotelbooking.reservationservice.service

import kissloryshy.hotelbooking.reservationservice.entity.Client
import kissloryshy.hotelbooking.reservationservice.entity.dto.ClientCountDto
import kissloryshy.hotelbooking.reservationservice.entity.dto.ClientDto
import kissloryshy.hotelbooking.reservationservice.mapper.ClientMapper
import kissloryshy.hotelbooking.reservationservice.repository.ClientRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mapstruct.factory.Mappers
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
        val client1 =
            ClientDto("testUn1", "testFn1", "testLn1", "kissloryshy1@gmail.com", "+79044477899", LocalDate.now())
        val client2 =
            ClientDto("testUn2", "testFn2", "testLn2", "kissloryshy2@gmail.com", "+79044477899", LocalDate.now())
        val clientDtoList = mutableListOf(client1, client2)
        val converter = Mappers.getMapper(ClientMapper::class.java)
        val clientEntityList = mutableListOf(converter.toModel(client1), converter.toModel(client2))

        `when`(clientRepository.findAll()).thenReturn(clientEntityList)

        val returnedClients = clientService.getAll()

        assertEquals(clientDtoList, returnedClients)
    }

    @Test
    fun getByUsername() {
        val client = Client()
        client.clientId = 1
        client.username = "testUn1"
        client.firstName = "testFn1"
        client.lastName = "testLn1"
        client.email = "kissloryshy1@gmail.com"
        client.phoneNumber = "+79044477899"
        client.birthdate = LocalDate.now()
        val username = "testUn1"

        `when`(clientRepository.findClientByUsername(username)).thenReturn(client)

        val returnedClient = clientService.getByUsername(username)

        assertEquals(client, returnedClient)
    }
}