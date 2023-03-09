package kissloryshy.hotelbooking.reservationservice.entity

import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal

@Entity
@Table(name = "rooms")
open class Room() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id", nullable = false)
    open var roomId: Long? = null

    @NotNull
    @Column(name = "number", nullable = false)
    open var number: Int? = null

    @NotNull
    @Column(name = "capacity", nullable = false)
    open var capacity: Int? = null

    @NotNull
    @Column(name = "class", nullable = false)
    open var classField: Int? = null

    @NotNull
    @Column(name = "is_enabled", nullable = false)
    open var isEnabled: Boolean? = false

    @NotNull
    @Column(name = "weekday_cost", nullable = false, precision = 12, scale = 2)
    open var weekdayCost: BigDecimal? = null

    @NotNull
    @Column(name = "holiday_cost", nullable = false, precision = 12, scale = 2)
    open var holidayCost: BigDecimal? = null

    @JsonManagedReference(value = "room")
    @OneToMany(mappedBy = "room")
    open var reservations: MutableSet<Reservation> = mutableSetOf()

    constructor(
        roomId: Long?,
        number: Int?,
        capacity: Int?,
        classField: Int?,
        isEnabled: Boolean?,
        weekdayCost: BigDecimal?,
        holidayCost: BigDecimal?
    ) : this() {
        this.roomId = roomId
        this.number = number
        this.capacity = capacity
        this.classField = classField
        this.isEnabled = isEnabled
        this.weekdayCost = weekdayCost
        this.holidayCost = holidayCost
    }

}
