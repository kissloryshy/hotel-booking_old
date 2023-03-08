package kissloryshy.hotelbooking.notificationservice.consumer

import kissloryshy.hotelbooking.notificationservice.entity.ClientDto
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class KafkaConsumer {
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
    fun clientNotification(clientDto: ClientDto) {
        logger.info("Client received {}", clientDto)
    }
}
