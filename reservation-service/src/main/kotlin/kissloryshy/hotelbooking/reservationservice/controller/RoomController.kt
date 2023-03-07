package kissloryshy.hotelbooking.reservationservice.controller

import kissloryshy.hotelbooking.reservationservice.entity.Room
import kissloryshy.hotelbooking.reservationservice.entity.dto.RoomCountDto
import kissloryshy.hotelbooking.reservationservice.exception.exceptions.RoomNotFoundException
import kissloryshy.hotelbooking.reservationservice.exception.exceptions.WrongParamsException
import kissloryshy.hotelbooking.reservationservice.service.RoomService
import org.springframework.web.bind.annotation.*

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
    fun getByRoomNumber(@PathVariable(value = "roomNumber") roomNumber: Int): Room {
        if (roomNumber < 0L) {
            throw WrongParamsException("Room number cannot be negative. Received number: $roomNumber")
        }

        return roomService.getByRoomNumber(roomNumber)
            ?: throw RoomNotFoundException("Room not found with number: $roomNumber")
    }

    @PostMapping("/create")
    fun create(@RequestBody room: Room): Room {
        return roomService.create(room)
    }
}