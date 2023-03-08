package kissloryshy.hotelbooking.reservationservice.service

import kissloryshy.hotelbooking.reservationservice.entity.dto.ReservationCountDto
import kissloryshy.hotelbooking.reservationservice.entity.dto.ReservationDto
import kissloryshy.hotelbooking.reservationservice.mapper.ReservationMapper
import kissloryshy.hotelbooking.reservationservice.repository.ReservationRepository
import org.mapstruct.factory.Mappers
import org.springframework.stereotype.Service

@Service
class ReservationService(
    private val reservationRepository: ReservationRepository
) {
    private var converter = Mappers.getMapper(ReservationMapper::class.java)

    fun getCount(): ReservationCountDto {
        return ReservationCountDto(reservationRepository.count())
    }

    fun getAll(): List<ReservationDto> {
        return reservationRepository.findAll().map { converter.toDto(it) }
    }

    fun getByClientUsername(username: String): List<ReservationDto> {
        return reservationRepository.findByClient_Username(username).map { converter.toDto(it) }
    }

    fun getByRoomNumber(number: Int): List<ReservationDto> {
        return reservationRepository.findByRoom_Number(number).map { converter.toDto(it) }
    }

    fun create(reservationDto: ReservationDto): ReservationDto {
        return converter.toDto(reservationRepository.save(converter.toModel(reservationDto)))
    }
}
