package kissloryshy.hotelbooking.reservationservice.exception

class ClientNotFoundException(
    override val message: String
) : RuntimeException(message)
