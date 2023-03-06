package kissloryshy.hotelbooking.reservationservice.exception

class RoomNotFoundException(
    override val message: String
) : RuntimeException(message)