package kissloryshy.hotelbooking.reservationservice.entity.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.jetbrains.annotations.NotNull
import java.time.LocalDate

data class ClientDto(

    @field:NotNull
    @field:NotBlank(message = "Invalid username: empty name")
    @field:Size(min = 4, message = "Invalid username: must be of 4 - 32 characters")
    val username: String,

    @field:NotNull
    @field:Size(min = 2)
    val firstName: String,

    @field:NotNull
    @field:Size(min = 2)
    val lastName: String,

    @field:NotNull
    @field:Size(min = 6)
    @field:Email(message = "Invalid email")
    val email: String,

    @field:NotNull
    @field:Size(min = 12, max = 15)
    val phoneNumber: String,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer::class)
    @JsonDeserialize(using = LocalDateDeserializer::class)
    @field:NotNull
    val birthdate: LocalDate

)
