package kissloryshy.hotelbooking.reservationservice.repository

import kissloryshy.hotelbooking.reservationservice.entity.Client
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ClientRepository : JpaRepository<Client, String> {
    fun findClientByUsername(username: String): Client?
}
