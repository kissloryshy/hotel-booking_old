package kissloryshy.hotelbooking.notificationservice.consumer

import kissloryshy.hotelbooking.notificationservice.entity.Client
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class KafkaConsumer {
    private val logger = LoggerFactory.getLogger(javaClass)

    @KafkaListener(topics = ["messageTopic"], groupId = "messageId", containerFactory = "messageKafkaListenerContainerFactory")
    fun messageNotification(message: String) {
        logger.info("Message received {}", message)
    }

    @KafkaListener(topics = ["clientTopic"], groupId = "clientGroupId", containerFactory = "clientKafkaListenerContainerFactory")
    fun clientNotification(client: Client) {
        logger.info("Client received {}", client)
    }
}
