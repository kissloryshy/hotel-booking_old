package kissloryshy.hotelbooking.reservationservice.entity

import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "clients")
open class Client() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id", nullable = false)
    open var clientId: Long? = null

    @Column(name = "username", nullable = false, length = 32)
    open var username: String? = null

    @Column(name = "first_name", nullable = false, length = 32)
    open var firstName: String? = null

    @Column(name = "last_name", nullable = false, length = 32)
    open var lastName: String? = null

    @Column(name = "email", nullable = false, length = 32)
    open var email: String? = null

    @Column(name = "phone_number", nullable = false, length = 20)
    open var phoneNumber: String? = null

    @Column(name = "birthdate", nullable = false)
    open var birthdate: LocalDate? = null

    @JsonManagedReference(value = "client")
    @OneToMany(mappedBy = "client")
    open var reservations: MutableSet<Reservation> = mutableSetOf()

    constructor(
        clientId: Long?,
        username: String?,
        firstName: String?,
        lastName: String?,
        email: String?,
        phoneNumber: String?,
        birthdate: LocalDate?
    ) : this() {
        this.clientId = clientId
        this.username = username
        this.firstName = firstName
        this.lastName = lastName
        this.email = email
        this.phoneNumber = phoneNumber
        this.birthdate = birthdate
    }

}
