package kissloryshy.hotelbooking.reservationservice.exception.exceptions

class RoomNotFoundException(
    override val message: String
) : RuntimeException(message)