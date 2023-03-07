package kissloryshy.hotelbooking.reservationservice.entity

import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.LocalDate

@Entity
@Table(name = "clients")
open class Client() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id", nullable = false)
    open var clientId: Long? = null

    @Size(max = 128)
    @NotNull
    @Column(name = "username", nullable = false, length = 128)
    open var username: String? = null

    @Size(max = 128)
    @NotNull
    @Column(name = "first_name", nullable = false, length = 128)
    open var firstName: String? = null

    @Size(max = 128)
    @NotNull
    @Column(name = "last_name", nullable = false, length = 128)
    open var lastName: String? = null

    @Size(max = 128)
    @NotNull
    @Column(name = "email", nullable = false, length = 128)
    open var email: String? = null

    @Size(max = 30)
    @NotNull
    @Column(name = "phone_number", nullable = false, length = 30)
    open var phoneNumber: String? = null

    @NotNull
    @Column(name = "birthdate", nullable = false)
    open var birthdate: LocalDate? = null

    @JsonManagedReference
    @OneToMany(mappedBy = "client")
    open var reservations: MutableSet<Reservation> = mutableSetOf()

    constructor(
        username: String?,
        firstName: String?,
        lastName: String?,
        email: String?,
        phoneNumber: String?,
        birthdate: LocalDate?,
        reservations: MutableSet<Reservation>
    ) : this() {
        this.username = username
        this.firstName = firstName
        this.lastName = lastName
        this.email = email
        this.phoneNumber = phoneNumber
        this.birthdate = birthdate
        this.reservations = reservations
    }
}