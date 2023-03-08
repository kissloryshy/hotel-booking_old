package kissloryshy.hotelbooking.reservationservice.entity.dto

import jakarta.validation.constraints.Min
import java.math.BigDecimal

data class RoomDto(

    @field:Min(value = 0)
    val number: Int,

    val capacity: Int,

    val classField: Int,

    val isEnabled: Boolean,

    val weekdayCost: BigDecimal,

    val holidayCost: BigDecimal

)
