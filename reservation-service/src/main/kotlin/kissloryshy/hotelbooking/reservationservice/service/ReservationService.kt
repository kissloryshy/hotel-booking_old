package kissloryshy.hotelbooking.reservationservice.service

import kissloryshy.hotelbooking.reservationservice.entity.Reservation
import kissloryshy.hotelbooking.reservationservice.repository.ReservationRepository
import org.springframework.stereotype.Service

@Service
class ReservationService(
    private val reservationRepository: ReservationRepository
) {
    fun getCount(): Long {
        return reservationRepository.count()
    }

    fun getAll(): List<Reservation> {
        return reservationRepository.findAll()
    }

    fun getById(reservationId: Long): Reservation {
        return if (reservationRepository.findById(reservationId).isPresent) {
            reservationRepository.findById(reservationId).get()
        } else {
            Reservation()
        }
    }
}