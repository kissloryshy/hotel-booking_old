package kissloryshy.hotelbooking.reservationservice.exception.exceptions

class ClientNotFoundException(
    override val message: String
) : RuntimeException(message)
