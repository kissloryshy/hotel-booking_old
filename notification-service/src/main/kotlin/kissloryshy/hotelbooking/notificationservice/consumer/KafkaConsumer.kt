package kissloryshy.hotelbooking.notificationservice.consumer

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import kissloryshy.hotelbooking.notificationservice.entity.ClientDto
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.converter.JsonMessageConverter
import org.springframework.stereotype.Component

@Component
class KafkaConsumer(
    private val jsonConverter: JsonMessageConverter
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @KafkaListener(
        topics = ["messageTopic"],
        groupId = "messageId",
        containerFactory = "messageKafkaListenerContainerFactory"
    )
    fun messageNotification(message: String) {
        logger.info("Message received {}", message)
    }

    @KafkaListener(
        topics = ["clientDtoTopic"],
        groupId = "clientDtoGroupId",
        containerFactory = "clientDtoKafkaListenerContainerFactory"
    )
    fun clientNotification(clientDto: String) {
        logger.info("Client raw received {}", clientDto)
        logger.info("Client mapped received {}", ObjectMapper().registerModule(JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).readValue(clientDto, ClientDto::class.java))
        logger.info("Client username received {}", ObjectMapper().registerModule(JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).readValue(clientDto, ClientDto::class.java).username)
    }
}
