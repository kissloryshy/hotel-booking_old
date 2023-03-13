package kissloryshy.hotelbooking.reservationservice.controller

import jakarta.validation.Valid
import kissloryshy.hotelbooking.reservationservice.entity.dto.ReservationCountDto
import kissloryshy.hotelbooking.reservationservice.entity.dto.ReservationDto
import kissloryshy.hotelbooking.reservationservice.service.ReservationService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/reservations")
class ReservationController(
    private val reservationService: ReservationService
) {
    @GetMapping("/getCount")
    fun getCount(): ReservationCountDto {
        return reservationService.getCount()
    }

    @GetMapping("/getAll")
    fun getAll(): List<ReservationDto> {
//        TODO paginated
        return reservationService.getAll()
    }

    @GetMapping("/getByClientUsername/{username}")
    fun getByClientUsername(@PathVariable("username") username: String): ResponseEntity<List<ReservationDto>> {
        return ResponseEntity.ok(reservationService.getByClientUsername(username))
    }

    @GetMapping("/getByRoomNumber/{number}")
    fun getByRoomNumber(@PathVariable("number") number: Int): ResponseEntity<List<ReservationDto>> {
        return ResponseEntity.ok(reservationService.getByRoomNumber(number))
    }

    @PostMapping("/check")
    fun checkDates(@Valid @RequestBody reservationDto: ReservationDto): ResponseEntity<Boolean> {
        return ResponseEntity(reservationService.checkDates(reservationDto), HttpStatus.OK)
    }

    @PostMapping("/create")
    fun create(@Valid @RequestBody reservationDto: ReservationDto): ResponseEntity<ReservationDto> {
        return reservationService.create(reservationDto)
    }
}
