package kissloryshy.hotelbooking.reservationservice.service

import kissloryshy.hotelbooking.reservationservice.entity.Reservation
import kissloryshy.hotelbooking.reservationservice.entity.dto.ReservationCountDto
import kissloryshy.hotelbooking.reservationservice.repository.ReservationRepository
import org.springframework.stereotype.Service

@Service
class ReservationService(
    private val reservationRepository: ReservationRepository
) {
    fun getReservationsCount(): ReservationCountDto {
        return ReservationCountDto(reservationRepository.count())
    }

    fun getAllReservations(): List<Reservation> {
        return reservationRepository.findAll()
    }

    fun getReservationById(reservationId: Long): Reservation {
        return if (reservationRepository.findById(reservationId).isPresent) {
            reservationRepository.findById(reservationId).get()
        } else {
            Reservation()
        }
    }
}