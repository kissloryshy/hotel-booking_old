package kissloryshy.hotelbooking.reservationservice.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import java.time.LocalDate

@Entity
@Table(name = "reservations")
open class Reservation() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id", nullable = false)
    open var reservationId: Long? = null

    @JsonBackReference
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    open var client: Client? = null

    @JsonBackReference
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_id", nullable = false)
    open var room: Room? = null

    @NotNull
    @Column(name = "contract_signed", nullable = false)
    open var contractSigned: LocalDate? = null

    @NotNull
    @Column(name = "reservation_start", nullable = false)
    open var reservationStart: LocalDate? = null

    @NotNull
    @Column(name = "reservation_end", nullable = false)
    open var reservationEnd: LocalDate? = null

    constructor(
        client: Client?,
        room: Room?,
        contractSigned: LocalDate?,
        reservationStart: LocalDate?,
        reservationEnd: LocalDate?
    ) : this() {
        this.client = client
        this.room = room
        this.contractSigned = contractSigned
        this.reservationStart = reservationStart
        this.reservationEnd = reservationEnd
    }
}