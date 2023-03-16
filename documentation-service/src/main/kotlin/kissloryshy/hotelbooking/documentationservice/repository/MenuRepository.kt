package kissloryshy.hotelbooking.documentationservice.repository

import kissloryshy.hotelbooking.documentationservice.document.Menu
import org.springframework.data.mongodb.repository.MongoRepository

interface MenuRepository : MongoRepository<Menu, String> {
    fun findByName(name: String): Menu?

//    fun findAll(): List<Menu>
}
