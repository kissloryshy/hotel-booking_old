package kissloryshy.hotelbooking.reservationservice.controller

import kissloryshy.hotelbooking.reservationservice.entity.Client
import kissloryshy.hotelbooking.reservationservice.entity.dto.ClientCountDto
import kissloryshy.hotelbooking.reservationservice.service.ClientService
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/clients")
class ClientController(
    private val clientService: ClientService,
    private val kafkaTemplate: KafkaTemplate<String, String>
) {
    @GetMapping("/getCount")
    fun getCount(): ClientCountDto {
        kafkaTemplate.send("messageTopic", "called: api clients get count")
        return clientService.getCount()
    }

    @GetMapping("/getAll")
    fun getAll(): List<Client> {
        return clientService.getAll()
    }

    @GetMapping("/getByUsername/{username}")
    fun getByUsername(@PathVariable(value = "username") username: String): Client {
        return clientService.getByUsername(username)
    }
}
