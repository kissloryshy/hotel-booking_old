package kissloryshy.hotelbooking.reservationservice.mapper

import kissloryshy.hotelbooking.reservationservice.entity.Client
import kissloryshy.hotelbooking.reservationservice.entity.dto.ClientDto
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper
interface ClientMapper {

    fun toDto(client: Client): ClientDto

    @Mapping(target = "reservations", ignore = true)
    fun toModel(clientDto: ClientDto): Client

}