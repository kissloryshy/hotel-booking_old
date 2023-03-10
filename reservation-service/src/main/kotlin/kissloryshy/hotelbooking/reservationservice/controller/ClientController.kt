package kissloryshy.hotelbooking.reservationservice.controller

import jakarta.validation.Valid
import kissloryshy.hotelbooking.reservationservice.entity.dto.ClientCountDto
import kissloryshy.hotelbooking.reservationservice.entity.dto.ClientDto
import kissloryshy.hotelbooking.reservationservice.exception.exceptions.ClientNotFoundException
import kissloryshy.hotelbooking.reservationservice.service.ClientService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/clients")
class ClientController(
    private val clientService: ClientService,
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val clientKafkaTemplate: KafkaTemplate<String, ClientDto>
) {
    @GetMapping("/getCount")
    fun getCount(): ClientCountDto {
        kafkaTemplate.send("messageTopic", "called: api clients get count")
        return clientService.getCount()
    }

    @GetMapping("/getAll")
    fun getAll(): ResponseEntity<List<ClientDto>> {
//        TODO paginated
        return ResponseEntity.ok(clientService.getAll())
    }

    @GetMapping("/getByUsername/{username}")
    fun getByUsername(@PathVariable(value = "username") username: String): ResponseEntity<ClientDto> {
        val clientDto: ClientDto? = clientService.getByUsername(username)
        if (clientDto != null) {
//            clientKafkaTemplate.send("clientDtoTopic", clientDto)
            return ResponseEntity.ok(clientDto)
        } else {
            throw ClientNotFoundException("Client not found with username: $username")
        }
    }

    @PostMapping("/create")
    fun create(@Valid @RequestBody clientDto: ClientDto): ResponseEntity<ClientDto> {
        return ResponseEntity(clientService.create(clientDto), HttpStatus.CREATED)
    }

}
