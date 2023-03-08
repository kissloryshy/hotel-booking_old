package kissloryshy.hotelbooking.reservationservice.entity.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import jakarta.validation.constraints.NotBlank
import kissloryshy.hotelbooking.reservationservice.entity.Client
import kissloryshy.hotelbooking.reservationservice.entity.Room
import org.jetbrains.annotations.NotNull
import java.time.LocalDate

data class ReservationDto(

    @field:NotNull
    @field:NotBlank(message = "Invalid client: empty client")
    val client: Client,

    @field:NotNull
    @field:NotBlank(message = "Invalid room: empty room")
    val room: Room,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer::class)
    @JsonDeserialize(using = LocalDateDeserializer::class)
    @field:NotNull
    @field:NotBlank(message = "Invalid contractSigned: empty contractSigned")
    val contractSigned: LocalDate,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer::class)
    @JsonDeserialize(using = LocalDateDeserializer::class)
    @field:NotNull
    @field:NotBlank(message = "Invalid reservationStart: empty reservationStart")
    val reservationStart: LocalDate,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer::class)
    @JsonDeserialize(using = LocalDateDeserializer::class)
    @field:NotNull
    @field:NotBlank(message = "Invalid reservationEnd: empty reservationEnd")
    val reservationEnd: LocalDate

)
