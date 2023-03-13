package kissloryshy.hotelbooking.testdatagenerator

import com.github.javafaker.Faker
import java.text.SimpleDateFormat
import java.util.*

fun main() {
    generateClient(200)
//    generateRooms(200)
}

fun generateClient(count: Int) {
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    val faker = Faker(Locale("en_US"))


    val out = StringBuilder("")
    out.append("insert into clients (username, first_name, last_name, email, phone_number, birthdate) \n")
    out.append("values ")
    for (i in 0 until count) {
        val fn = faker.name().firstName().replace("'", "")
        val ln = faker.name().lastName().replace("'", "")

        out.append("('")
        out.append("$fn$ln".lowercase())
        out.append("', '")
        out.append(fn)
        out.append("', '")
        out.append(ln)
        out.append("', '")
        out.append(faker.internet().emailAddress())
        out.append("', '")
        out.append("+7" + (1000000000..9999999999).random())
        out.append("', '")
        out.append(sdf.format(faker.date().birthday(14, 60)))
        out.append("'), \n")
    }
    out.deleteCharAt(out.length - 1)
    out.deleteCharAt(out.length - 1)
    out.deleteCharAt(out.length - 1)

    out.append(";")

    println(out)
}

fun generateRooms(count: Int) {
    val out = StringBuilder("")
    out.append("insert into rooms (number, capacity, class, is_enabled, weekday_cost, holiday_cost) \n")
    out.append("values ")
    for (i in 0 until count) {
        out.append("(")
        out.append(i + 5)
        out.append(", ")
        out.append((1..5).random())
        out.append(", ")
        out.append((1..4).random())
        out.append(", ")
        out.append((0..1).random() == 0)
        out.append(", '")
        val cost = (1000..19999).random()
        out.append(cost)
        out.append("', '")
        out.append((cost * kotlin.random.Random.nextDouble(1.0, 5.0)).toInt())
        out.append("'), \n")
    }
    out.deleteCharAt(out.length - 1)
    out.deleteCharAt(out.length - 1)
    out.deleteCharAt(out.length - 1)

    out.append(";")

    println(out)
}
