package kissloryshy.hotelbooking.reservationservice.controller

import com.github.dockerjava.api.model.ExposedPort
import com.github.dockerjava.api.model.HostConfig
import com.github.dockerjava.api.model.PortBinding
import com.github.dockerjava.api.model.Ports
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName

abstract class AbstractIntegrationTest {

    companion object {
        private val localPort = 10100
        private val containerPort = 5432
        val postgres = PostgreSQLContainer(DockerImageName.parse("postgres:15.2"))

        init {
            postgres.apply {
                withDatabaseName("hotel-booking")
                withUsername("postgres")
                withPassword("1212")
                withExposedPorts(containerPort)
                withCommand("-p $containerPort")
                withCreateContainerCmdModifier { cmd ->
                    cmd.withHostConfig(
                        HostConfig().withPortBindings(
                            PortBinding(Ports.Binding.bindPort(localPort), ExposedPort(containerPort))
                        )
                    )
                }
            }
            postgres.start()
        }

        @DynamicPropertySource
        fun overrideTestProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", postgres::getJdbcUrl)
            registry.add("spring.datasource.username", postgres::getUsername)
            registry.add("spring.datasource.password", postgres::getPassword)
        }

    }

}