package kissloryshy.hotelbooking.reservationservice.service

import kissloryshy.hotelbooking.reservationservice.entity.Room
import kissloryshy.hotelbooking.reservationservice.entity.dto.RoomCountDto
import kissloryshy.hotelbooking.reservationservice.repository.RoomRepository
import org.springframework.stereotype.Service

@Service
class RoomService(
    private val roomRepository: RoomRepository
) {
    fun getRoomCount(): RoomCountDto {
        return RoomCountDto(roomRepository.count())
    }

    fun getAllRooms(): List<Room> {
        return roomRepository.findAll()
    }

    fun getByRoomNumber(roomNumber: Long): Room {
        return if (roomRepository.findByRoomNumber(roomNumber).isPresent) {
            roomRepository.findByRoomNumber(roomNumber).get()
        } else {
            Room()
        }
    }
}