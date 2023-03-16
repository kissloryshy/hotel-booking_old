package kissloryshy.hotelbooking.documentationservice.service

import kissloryshy.hotelbooking.documentationservice.document.Menu
import kissloryshy.hotelbooking.documentationservice.repository.MenuRepository
import org.springframework.stereotype.Service

@Service
class MenuService(
    private val menuRepository: MenuRepository
) {
    fun findByName(name: String): Menu? {
        return menuRepository.findByName(name)
    }

    fun create(menu: Menu) {
        menuRepository.save(menu)
    }
}
