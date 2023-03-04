package kissloryshy.hotelbooking.notificationservice

import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class KafkaConsumer {
    private val logger = LoggerFactory.getLogger(javaClass)

    @KafkaListener(topics = ["messageTopic"], groupId = "messageId")
    fun messageNotification(message: String) {
        logger.info("Message received {}", message)
    }
}
