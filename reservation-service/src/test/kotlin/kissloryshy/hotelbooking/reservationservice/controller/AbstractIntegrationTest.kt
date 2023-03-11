package kissloryshy.hotelbooking.reservationservice.controller

import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.utility.DockerImageName

abstract class AbstractIntegrationTest {

    companion object {

//        @Container
        val postgres = PostgreSQLContainer(DockerImageName.parse("postgres:11.6"))

//        @DynamicPropertySource
        fun overrideTestProperties(registry: DynamicPropertyRegistry) {
//            registry.add("spring.datasource.url", postgres::getJdbcUrl)
            registry.add("spring.datasource.username", postgres::getUsername)
            registry.add("spring.datasource.password", postgres::getPassword)
        }

    }

}