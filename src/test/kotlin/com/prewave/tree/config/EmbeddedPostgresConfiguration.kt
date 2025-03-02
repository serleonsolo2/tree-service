package com.prewave.tree.config

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.containers.output.Slf4jLogConsumer
import org.testcontainers.containers.wait.strategy.Wait

@TestConfiguration
@Import
class EmbeddedPostgresConfiguration(
    @Value("\${db.name}")
    val dbName: String,
    @Value("\${db.username}")
    val username: String,
    @Value("\${db.password}")
    val password: String,
) {
    @Bean
    fun postgresContainer(configurableApplicationContext: ConfigurableApplicationContext): PostgreSQLContainer<*> {
        val container =
            PostgreSQLContainer("postgres:14.6")
                .withDatabaseName(dbName)
                .withUsername(username)
                .withPassword(password)
                .withLogConsumer(
                    Slf4jLogConsumer(
                        LoggerFactory.getLogger("POSTGRES"),
                    ),
                ).waitingFor(Wait.forListeningPort())

        container.start()

        container.also {
            TestPropertyValues
                .of(
                    "spring.datasource.url=" + it.jdbcUrl,
                    "spring.datasource.username=" + it.username,
                    "spring.datasource.password=" + it.password,
                ).applyTo(configurableApplicationContext.environment)
        }

        return container
    }
}
