package kissloryshy.hotelbooking.reservationservice.exception

class ReservationNotFoundException(
    override val message: String
) : RuntimeException(message)