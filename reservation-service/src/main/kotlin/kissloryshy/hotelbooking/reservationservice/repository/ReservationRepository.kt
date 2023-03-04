package kissloryshy.hotelbooking.reservationservice.repository

import kissloryshy.hotelbooking.reservationservice.entity.Reservation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReservationRepository : JpaRepository<Reservation, Long> {
    fun findByReservationId(reservationId: Long): Reservation
}