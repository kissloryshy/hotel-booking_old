package kissloryshy.hotelbooking.reservationservice.repository

import kissloryshy.hotelbooking.reservationservice.entity.Reservation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface ReservationRepository : JpaRepository<Reservation, Long> {
    fun findByClientUsername(username: String): List<Reservation>

    fun findByRoomNumber(number: Int): List<Reservation>

    /**
     * @return return true if dates are free for reservation and false if dates are busy
     */
    @Query(
        value = """
        select not exists(select 1
            from reservations
            where room_id = 2
            and (:startDate between reservation_start and reservation_end
            or :endDate between reservation_start and reservation_end))
    """, nativeQuery = true
    )
    fun checkDates(
        @Param("startDate") startDate: LocalDate,
        @Param("endDate") endDate: LocalDate
    ): Boolean
}
