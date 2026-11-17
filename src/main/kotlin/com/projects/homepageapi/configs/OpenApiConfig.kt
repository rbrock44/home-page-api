package com.projects.homepageapi.configs

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.PathItem
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springdoc.core.customizers.OpenApiCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

const val API_KEY_SCHEME = "apiKey"

// Served at /swagger-ui.html (browsable) and /v3/api-docs (raw OpenAPI JSON).
@Configuration
class OpenApiConfig {

    @Bean
    fun openApi(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("Home Page API")
                    .description("Backend for Home Page, Cleaning Schedule, Flash Cards, Utilities and Family Recipes")
                    .version("1.0.0")
            )
            .components(
                Components().addSecuritySchemes(
                    API_KEY_SCHEME,
                    SecurityScheme()
                        .type(SecurityScheme.Type.APIKEY)
                        .`in`(SecurityScheme.In.HEADER)
                        .name("X-API-Key")
                )
            )
    }

    // Marks the routes ApiKeyInterceptor protects, so Swagger UI shows the lock and sends the header.
    @Bean
    fun apiKeyRoutesCustomizer(): OpenApiCustomizer {
        return OpenApiCustomizer { openApi ->
            openApi.paths?.forEach { (path, item) ->
                item.readOperationsMap().forEach { (method, operation) ->
                    if (requiresApiKey(path, method)) {
                        operation.addSecurityItem(SecurityRequirement().addList(API_KEY_SCHEME))
                    }
                }
            }
        }
    }

    private fun requiresApiKey(path: String, method: PathItem.HttpMethod): Boolean {
        return when {
            path.startsWith("/recipe/pending") ->
                method in setOf(PathItem.HttpMethod.GET, PathItem.HttpMethod.POST, PathItem.HttpMethod.DELETE)
            path.startsWith("/cleaning-schedule") ->
                method in setOf(PathItem.HttpMethod.POST, PathItem.HttpMethod.DELETE)
            else -> false
        }
    }
}
