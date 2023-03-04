package kissloryshy.hotelbooking.reservationservice.entity

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "reservations")
open class Reservation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id", nullable = false)
    open var reservationId: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_username")
    open var client: Client? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_number")
    open var room: Room? = null,

    @Column(name = "contract_signed")
    open var contractSigned: LocalDate? = null,

    @Column(name = "reservation_start")
    open var reservationStart: LocalDate? = null,

    @Column(name = "reservation_end")
    open var reservationEnd: LocalDate? = null
)