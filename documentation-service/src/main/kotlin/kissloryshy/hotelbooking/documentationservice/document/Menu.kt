package kissloryshy.hotelbooking.documentationservice.document

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("menu")
class Menu() {
    @Id
    private lateinit var menuId: String

    private lateinit var name: String

    private lateinit var calories: Integer

    constructor(
        menuId: String,
        name: String,
        calories: Integer
    ) : this() {
        this.menuId = menuId
        this.name = name
        this.calories = calories
    }
}
