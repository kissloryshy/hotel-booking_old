package kissloryshy.hotelbooking.reservationservice.controller

import jakarta.validation.Valid
import kissloryshy.hotelbooking.reservationservice.entity.dto.RoomCountDto
import kissloryshy.hotelbooking.reservationservice.entity.dto.RoomDto
import kissloryshy.hotelbooking.reservationservice.service.RoomService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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

    @GetMapping("/getAll/{page}/{size}")
    fun getAll(@PathVariable("page") page: Int, @PathVariable("size") size: Int): List<RoomDto> {
        return roomService.getAll(page, size)
    }

    @GetMapping("/getByNumber/{roomNumber}")
    fun getByRoomNumber(@Valid @PathVariable(value = "roomNumber") roomNumber: Int): ResponseEntity<RoomDto> {
        return ResponseEntity(roomService.getByNumber(roomNumber), HttpStatus.OK)
    }

    @PostMapping("/create")
    fun create(@RequestBody roomDto: RoomDto): ResponseEntity<RoomDto> {
        return ResponseEntity(roomService.create(roomDto), HttpStatus.CREATED)
    }
}