package kissloryshy.hotelbooking.reservationservice.entity

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "clients")
class Client(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "username", nullable = false)
        var username: String? = null,

        @Column(name = "first_name")
        var firstName: String? = null,

        @Column(name = "last_name")
        var lastName: String? = null,

        @Column(name = "email")
        var email: String? = null,

        @Column(name = "phone_number")
        var phoneNumber: String? = null,

        @Column(name = "birthdate")
        var birthdate: LocalDate? = null
)