package kissloryshy.hotelbooking.reservationservice.controller

import jakarta.validation.Valid
import kissloryshy.hotelbooking.reservationservice.entity.Room
import kissloryshy.hotelbooking.reservationservice.entity.dto.RoomCountDto
import kissloryshy.hotelbooking.reservationservice.exception.exceptions.RoomNotFoundException
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
//        TODO paginated
        return roomService.getAll()
    }

    @GetMapping("/getByNumber/{roomNumber}")
    fun getByRoomNumber(@Valid @PathVariable(value = "roomNumber") roomNumber: Int): Room {

        return roomService.getByNumber(roomNumber)
            ?: throw RoomNotFoundException("Room not found with number: $roomNumber")
    }

    @PostMapping("/create")
    fun create(@RequestBody room: Room): Room {
        return roomService.create(room)
    }
}