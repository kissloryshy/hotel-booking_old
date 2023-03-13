package kissloryshy.hotelbooking.reservationservice.service

import kissloryshy.hotelbooking.reservationservice.entity.dto.ReservationCountDto
import kissloryshy.hotelbooking.reservationservice.entity.dto.ReservationDto
import kissloryshy.hotelbooking.reservationservice.mapper.ReservationMapper
import kissloryshy.hotelbooking.reservationservice.repository.ReservationRepository
import org.mapstruct.factory.Mappers
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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
        return reservationRepository.findByClientUsername(username).map { converter.toDto(it) }
    }

    fun getByRoomNumber(number: Int): List<ReservationDto> {
        return reservationRepository.findByRoomNumber(number).map { converter.toDto(it) }
    }

    fun checkDates(reservationDto: ReservationDto): Boolean {
        return reservationRepository.checkDates(reservationDto.reservationStart, reservationDto.reservationEnd)
    }

    fun create(reservationDto: ReservationDto): ResponseEntity<ReservationDto> {
        return if (reservationRepository.checkDates(reservationDto.reservationStart, reservationDto.reservationEnd)) {
            ResponseEntity(
                converter.toDto(reservationRepository.save(converter.toModel(reservationDto))),
                HttpStatus.CREATED
            )
        } else {
            ResponseEntity(reservationDto, HttpStatus.BAD_REQUEST)
        }
    }
}
