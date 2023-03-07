package kissloryshy.hotelbooking.reservationservice.exception.exceptions

class ReservationNotFoundException(
    override val message: String
) : RuntimeException(message)