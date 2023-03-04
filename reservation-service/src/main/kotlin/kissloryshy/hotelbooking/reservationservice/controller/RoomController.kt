package kissloryshy.hotelbooking.reservationservice.controller

import kissloryshy.hotelbooking.reservationservice.entity.Room
import kissloryshy.hotelbooking.reservationservice.service.RoomService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/reservation")
class RoomController(
    private val roomService: RoomService
) {
    @GetMapping("/getAllRooms")
    fun getAllRooms(): List<Room> {
        return roomService.getAllRooms()
    }

    @GetMapping("/getByRoomNuber/{roomNumber}")
    fun getByRoomNumber(@PathVariable(value = "roomNumber") roomNumber: Long): Room {
        return roomService.getByRoomNumber(roomNumber)
    }
}