package kissloryshy.hotelbooking.reservationservice.service

import kissloryshy.hotelbooking.reservationservice.entity.Client
import kissloryshy.hotelbooking.reservationservice.repository.ClientRepository
import org.springframework.stereotype.Service

@Service
class ClientService(
    private val clientRepository: ClientRepository
) {
    fun getAllClients(): List<Client> {
        return clientRepository.findAll()
    }

    fun getByUsername(username: String): Client {
        return clientRepository.findClientByUsername(username)
    }
}