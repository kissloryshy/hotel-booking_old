package kissloryshy.hotelbooking.reservationservice.exception

data class ErrorMessageModel(
    var status: Int,
    var message: String
)