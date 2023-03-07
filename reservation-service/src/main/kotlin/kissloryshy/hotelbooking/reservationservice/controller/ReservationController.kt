package kissloryshy.hotelbooking.reservationservice.controller

import kissloryshy.hotelbooking.reservationservice.entity.Reservation
import kissloryshy.hotelbooking.reservationservice.entity.dto.ReservationCountDto
import kissloryshy.hotelbooking.reservationservice.service.ReservationService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/reservations")
@Validated
class ReservationController(
    private val reservationService: ReservationService
) {
    @GetMapping("/getCount")
    fun getCount(): ReservationCountDto {
        return reservationService.getCount()
    }

    @GetMapping("/getAll")
    fun getAll(): List<Reservation> {
        return reservationService.getAll()
    }

    @PostMapping("/create")
    fun create(@RequestBody reservation: Reservation): Reservation {
        return reservationService.create(reservation)
    }
}