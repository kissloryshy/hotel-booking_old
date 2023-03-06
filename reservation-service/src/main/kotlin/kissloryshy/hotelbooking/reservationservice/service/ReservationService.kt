package kissloryshy.hotelbooking.reservationservice.service

import kissloryshy.hotelbooking.reservationservice.entity.Reservation
import kissloryshy.hotelbooking.reservationservice.entity.dto.ReservationCountDto
import kissloryshy.hotelbooking.reservationservice.repository.ReservationRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class ReservationService(
    private val reservationRepository: ReservationRepository
) {
    fun getCount(): ReservationCountDto {
        return ReservationCountDto(reservationRepository.count())
    }

    fun getAll(): List<Reservation> {
        return reservationRepository.findAll()
    }

    fun getById(reservationId: Long): Optional<Reservation> {
        return reservationRepository.findByReservationId(reservationId)
    }
}
