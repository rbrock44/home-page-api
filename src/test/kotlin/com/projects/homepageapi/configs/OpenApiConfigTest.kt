package com.projects.homepageapi.configs

import io.swagger.v3.oas.models.Operation
import io.swagger.v3.oas.models.PathItem
import io.swagger.v3.oas.models.Paths
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class OpenApiConfigTest {
    private val config = OpenApiConfig()

    @Test
    fun `should describe the X-API-Key header scheme`() {
        val scheme = config.openApi().components.securitySchemes[API_KEY_SCHEME]!!

        assertEquals("X-API-Key", scheme.name)
        assertEquals("header", scheme.`in`.toString())
    }

    @Test
    fun `should only lock the routes the api key interceptor protects`() {
        val recipe = PathItem().get(Operation()).post(Operation()).delete(Operation())
        val cleaning = PathItem().get(Operation()).post(Operation()).delete(Operation())
        val games = PathItem().get(Operation())
        val openApi = config.openApi().paths(
            Paths()
                .addPathItem("/recipe/pending", recipe)
                .addPathItem("/cleaning-schedule", cleaning)
                .addPathItem("/games-per-date/basketball/upcoming", games)
        )

        config.apiKeyRoutesCustomizer().customise(openApi)

        assertTrue(recipe.get.isLocked())
        assertTrue(recipe.post.isLocked())
        assertTrue(recipe.delete.isLocked())
        assertNull(cleaning.get.security)
        assertTrue(cleaning.post.isLocked())
        assertTrue(cleaning.delete.isLocked())
        assertNull(games.get.security)
    }

    private fun Operation.isLocked(): Boolean = security?.any { it.containsKey(API_KEY_SCHEME) } == true
}
