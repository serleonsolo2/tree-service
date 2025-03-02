package com.prewave.tree.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {
    @Bean
    fun swaggerInfo(): OpenAPI {
        val info =
            Info()
                .title("Tree Service API")
                .version("1.0")
                .description("This API exposes endpoints to the tree service.")
        return OpenAPI().info(info)
    }
}
