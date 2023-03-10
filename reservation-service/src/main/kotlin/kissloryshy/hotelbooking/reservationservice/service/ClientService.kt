package kissloryshy.hotelbooking.reservationservice.service

import kissloryshy.hotelbooking.reservationservice.entity.dto.ClientCountDto
import kissloryshy.hotelbooking.reservationservice.entity.dto.ClientDto
import kissloryshy.hotelbooking.reservationservice.mapper.ClientMapper
import kissloryshy.hotelbooking.reservationservice.repository.ClientRepository
import org.mapstruct.factory.Mappers
import org.springframework.stereotype.Service

@Service
class ClientService(
    private val clientRepository: ClientRepository
) {
    private val converter = Mappers.getMapper(ClientMapper::class.java)

    fun getCount(): ClientCountDto {
        return ClientCountDto(clientRepository.count())
    }

    fun getAll(): List<ClientDto> {
        return clientRepository.findAll().map { converter.toDto(it) }
    }

    fun getByUsername(username: String): ClientDto? {
        return clientRepository.findClientByUsername(username)?.let { converter.toDto(it) }
    }

    fun create(clientDto: ClientDto): ClientDto {
        return converter.toDto(clientRepository.save(converter.toModel(clientDto)))
    }
}