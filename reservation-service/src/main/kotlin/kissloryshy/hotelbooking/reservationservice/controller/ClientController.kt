package kissloryshy.hotelbooking.reservationservice.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/reservation")
class ClientController {
    @GetMapping
    fun hello(): String {
        return "hello from reservation-service, client controller"
    }
}
