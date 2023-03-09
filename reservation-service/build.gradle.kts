java.sourceCompatibility = JavaVersion.VERSION_17

tasks.getByName("bootJar") {
    enabled = true
}

tasks.getByName("jar") {
    enabled = false
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
}

plugins {
    id("org.flywaydb.flyway") version "9.15.1"
    jacoco
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client:+")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.flywaydb:flyway-core:9.15.1")
    runtimeOnly("org.postgresql:postgresql")
    implementation("jakarta.validation:jakarta.validation-api:3.0.2")
    implementation("org.hibernate.validator:hibernate-validator:8.0.0.Final")
    implementation("org.mapstruct:mapstruct:1.5.3.Final")
    kapt("org.mapstruct:mapstruct-processor:1.5.3.Final")

    implementation("org.springframework.kafka:spring-kafka:3.0.3")

    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:postgresql:1.17.6")
    testImplementation("org.testcontainers:junit-jupiter:1.17.6")
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
