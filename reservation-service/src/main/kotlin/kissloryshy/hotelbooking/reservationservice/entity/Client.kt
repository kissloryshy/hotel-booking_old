package kissloryshy.hotelbooking.reservationservice.entity

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "clients")
open class Client(
    @Id
    @Column(name = "username", nullable = false, length = 128)
    open var username: String? = null,

    @Column(name = "first_name", nullable = false, length = 128)
    open var firstName: String? = null,

    @Column(name = "last_name", nullable = false, length = 128)
    open var lastName: String? = null,

    @Column(name = "email", nullable = false, length = 128)
    open var email: String? = null,

    @Column(name = "phone_number", nullable = false, length = 30)
    open var phoneNumber: String? = null,

    @Column(name = "birthdate", nullable = false)
    open var birthdate: LocalDate? = null,

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    open var reservations: MutableSet<Reservation> = mutableSetOf()
)
