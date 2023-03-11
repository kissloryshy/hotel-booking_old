package kissloryshy.hotelbooking.notificationservice.entity

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import java.time.LocalDate

data class ClientDto(

    val username: String,

    val firstName: String,

    val lastName: String,

    val email: String,

    val phoneNumber: String,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer::class)
    @JsonDeserialize(using = LocalDateDeserializer::class)
    val birthdate: LocalDate

) {

    // Empty constructor for ObjectMapper
    constructor() : this("", "", "", "", "", LocalDate.now())

}
