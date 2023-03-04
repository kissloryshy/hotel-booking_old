package kissloryshy.hotelbooking.reservationservice.controller

import kissloryshy.hotelbooking.reservationservice.entity.Reservation
import kissloryshy.hotelbooking.reservationservice.service.ReservationService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/reservation")
class ReservationController(
    private val reservationService: ReservationService
) {
    @GetMapping("/getCount")
    fun getCount(): Long {
        return reservationService.getCount()
    }

    @GetMapping("/getAllReservations")
    fun getAllReservations(): List<Reservation> {
        return reservationService.getAll()
    }

    @GetMapping("/getById/{reservationId}")
    fun getById(@PathVariable(value = "reservationId") reservationId: Long): Reservation {
        return reservationService.getById(reservationId)
    }
}