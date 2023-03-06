package kissloryshy.hotelbooking.reservationservice.repository

import kissloryshy.hotelbooking.reservationservice.entity.Room
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RoomRepository : JpaRepository<Room, String> {
    fun findByRoomNumber(roomNumber: Long): Room?
}