package kissloryshy.hotelbooking.reservationservice.entity

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "rooms")
open class Room(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_number", nullable = false)
    open var roomNumber: Long? = null,

    @Column(name = "room_capacity")
    open var roomCapacity: Int? = null,

    @Column(name = "class")
    open var classField: Int? = null,

    @Column(name = "is_enabled")
    open var isEnabled: Boolean? = null,

    @Column(name = "weekday_cost", precision = 12, scale = 2)
    open var weekdayCost: BigDecimal? = null,

    @Column(name = "holiday_cost", precision = 12, scale = 2)
    open var holidayCost: BigDecimal? = null,

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    open var reservations: MutableSet<Reservation> = mutableSetOf()
)