package kissloryshy.hotelbooking.documentationservice.controller

import kissloryshy.hotelbooking.documentationservice.document.Menu
import kissloryshy.hotelbooking.documentationservice.service.MenuService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/docs")
class MenuController(
    private val menuService: MenuService
) {
    @GetMapping("/test/{message}")
    fun hello(@PathVariable("message") message: String): String {
        return "received message: $message"
    }

    @PostMapping("/create")
    fun create(@RequestBody menu: Menu) {
        menuService.create(menu)
    }
}
