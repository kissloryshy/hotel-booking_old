package kissloryshy.hotelbooking.reservationservice.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import jakarta.validation.Valid
import kissloryshy.hotelbooking.reservationservice.entity.dto.ClientCountDto
import kissloryshy.hotelbooking.reservationservice.entity.dto.ClientDto
import kissloryshy.hotelbooking.reservationservice.exception.exceptions.ClientNotFoundException
import kissloryshy.hotelbooking.reservationservice.service.ClientService
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate

@RestController
@RequestMapping("/api/clients")
class ClientController(
    private val clientService: ClientService,
    @Qualifier("messageKafkaTemplate") private val kafkaTemplate: KafkaTemplate<String, String>,
    @Qualifier("clientDtoKafkaTemplate") private val clientKafkaTemplate: KafkaTemplate<String, String>
) {
    @GetMapping("/getCount")
    fun getCount(): ClientCountDto {
        kafkaTemplate.send("messageTopic", "called: api clients get count")
        return clientService.getCount()
    }

    @GetMapping("/getAll/{page}/{size}")
    fun getAll(@PathVariable("page") page: Int, @PathVariable("size") size: Int): ResponseEntity<List<ClientDto>> {
        return ResponseEntity.ok(clientService.getAll(page, size))
    }

    @GetMapping("/getByUsername/{username}")
    fun getByUsername(@PathVariable(value = "username") username: String): ResponseEntity<ClientDto> {
        val clientDto: ClientDto? = clientService.getByUsername(username)
        if (clientDto != null) {
            clientKafkaTemplate.send(
                "clientDtoTopic",
                ObjectMapper()
                    .registerModule(JavaTimeModule())
                    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                    .writeValueAsString(clientDto)
            )
            return ResponseEntity.ok(clientDto)
        } else {
            throw ClientNotFoundException("Client not found with username: $username")
        }
    }

    @PostMapping("/create")
    fun create(@Valid @RequestBody clientDto: ClientDto): ResponseEntity<ClientDto> {
        return ResponseEntity(clientService.create(clientDto), HttpStatus.CREATED)
    }

    @PutMapping("/changeFirstName/{username}/{firstName}")
    fun changeFirstName(@PathVariable("username") username: String,
                        @PathVariable("firstName") firstName: String): ResponseEntity<Boolean> {
        return if(clientService.changeFirstName(username, firstName)) {
            ResponseEntity(true, HttpStatus.OK)
        } else {
            ResponseEntity(false, HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("/getTest/{message}")
    fun getTest(@PathVariable("message") message: String): ResponseEntity<String> {
        val restTemplate = RestTemplate()
        val resource = "http://localhost:9000/api/docs/"
        return restTemplate.getForEntity(resource + "test/hello-kissloryshy", String::class.java)
    }
}
