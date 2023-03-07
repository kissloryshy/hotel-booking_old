package kissloryshy.hotelbooking.notificationservice.entity

import java.time.LocalDate

data class Client(
    val clientId: Long,
    val username: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    val birthdate: LocalDate
)
