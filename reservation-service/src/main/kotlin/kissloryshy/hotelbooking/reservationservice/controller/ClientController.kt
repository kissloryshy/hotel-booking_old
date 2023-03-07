package kissloryshy.hotelbooking.reservationservice.controller

import kissloryshy.hotelbooking.reservationservice.entity.Client
import kissloryshy.hotelbooking.reservationservice.entity.dto.ClientCountDto
import kissloryshy.hotelbooking.reservationservice.exception.exceptions.ClientNotFoundException
import kissloryshy.hotelbooking.reservationservice.service.ClientService
import org.springframework.http.ResponseEntity
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/clients")
class ClientController(
    private val clientService: ClientService,
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val clientKafkaTemplate: KafkaTemplate<String, Client>
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
    fun getByUsername(@PathVariable(value = "username") username: String): ResponseEntity<Client> {
        val client: Client? = clientService.getByUsername(username)
        if (client != null) {
            clientKafkaTemplate.send("clientTopic", client)
            return ResponseEntity.ok(client)
        } else {
            throw ClientNotFoundException("Client not found with username: $username")
        }
    }

    @PostMapping("/create")
    fun create(@RequestBody client: Client): Client {
        return clientService.create(client)
    }
}
