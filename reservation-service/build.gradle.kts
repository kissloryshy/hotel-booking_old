tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}

plugins {
    id("org.flywaydb.flyway") version "9.15.1"
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.flywaydb:flyway-core:9.15.1")
    runtimeOnly("org.postgresql:postgresql")
    implementation("org.springframework.boot:spring-boot-devtools")
}

flyway {
    url = "jdbc:postgresql://localhost:10001/hotel-booking"
    driver = "org.postgresql.Driver"
    schemas = arrayOf("public")
    user = "postgres"
    password = "1212"
    cleanDisabled = false
}
