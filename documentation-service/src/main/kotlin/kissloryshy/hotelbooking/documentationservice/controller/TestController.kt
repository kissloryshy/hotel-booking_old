package kissloryshy.hotelbooking.documentationservice.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/docs")
class TestController {
    @GetMapping("/test/{message}")
    fun hello(@PathVariable("message") message: String): String {
        return "received message: $message"
    }
}
