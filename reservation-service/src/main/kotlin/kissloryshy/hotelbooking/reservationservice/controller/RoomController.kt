package kissloryshy.hotelbooking.reservationservice.controller

import kissloryshy.hotelbooking.reservationservice.entity.Room
import kissloryshy.hotelbooking.reservationservice.entity.dto.RoomCountDto
import kissloryshy.hotelbooking.reservationservice.exception.RoomNotFoundException
import kissloryshy.hotelbooking.reservationservice.service.RoomService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/rooms")
class RoomController(
    private val roomService: RoomService
) {
    @GetMapping("/getCount")
    fun getCount(): RoomCountDto {
        return roomService.getCount()
    }

    @GetMapping("/getAll")
    fun getAll(): List<Room> {
        return roomService.getAll()
    }

    @GetMapping("/getByRoomNumber/{roomNumber}")
    fun getByRoomNumber(@PathVariable(value = "roomNumber") roomNumber: Long): Room {
        val room = roomService.getByRoomNumber(roomNumber)
        if (room.isPresent) {
            return room.get()
        } else {
            throw RoomNotFoundException("Room not found with roomnumber: $roomNumber")
        }
    }
}