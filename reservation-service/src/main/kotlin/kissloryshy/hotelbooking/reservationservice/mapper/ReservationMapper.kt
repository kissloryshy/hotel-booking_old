package kissloryshy.hotelbooking.reservationservice.mapper

import kissloryshy.hotelbooking.reservationservice.entity.Reservation
import kissloryshy.hotelbooking.reservationservice.entity.dto.ReservationDto
import org.mapstruct.Mapper

@Mapper
interface ReservationMapper {

    fun toDto(reservation: Reservation): ReservationDto

    fun toModel(reservationDto: ReservationDto): Reservation

}
