package kissloryshy.hotelbooking.reservationservice.controller

import kissloryshy.hotelbooking.reservationservice.entity.Reservation
import kissloryshy.hotelbooking.reservationservice.entity.dto.ReservationCountDto
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
    @GetMapping("/getReservationsCount")
    fun getReservationsCount(): ReservationCountDto {
        return reservationService.getReservationsCount()
    }

    @GetMapping("/getAllReservations")
    fun getAllReservations(): List<Reservation> {
        return reservationService.getAllReservations()
    }

    @GetMapping("/getReservationById/{reservationId}")
    fun getById(@PathVariable(value = "reservationId") reservationId: Long): Reservation {
        return reservationService.getReservationById(reservationId)
    }
}