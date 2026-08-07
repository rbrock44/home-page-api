package com.projects.homepageapi.configs

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

@Component
class ApiKeyInterceptor(
    @Value("\${api.key:}") private val recipeApiKey: String,
    @Value("\${cleaning.schedule.api.key:}") private val cleaningScheduleApiKey: String
) : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val requestPath = request.requestURI
        val requiredKey = requiredKeyFor(requestPath, request.method)

        // Only endpoints with a required key configured above are protected
        if (requiredKey != null) {
            val providedKey = request.getHeader("X-API-Key")

            if (requiredKey.isEmpty() || providedKey != requiredKey) {
                response.status = HttpServletResponse.SC_UNAUTHORIZED
                response.contentType = "application/json"
                response.writer.write("""{"error":"Unauthorized - Invalid or missing API key"}""")
                return false
            }
        }
        return true
    }

    // Each protected app gets its own key, since these are all client-side SPAs -
    // a key shipped in one app's public JS bundle must not also unlock another app's endpoints.
    private fun requiredKeyFor(path: String, method: String): String? {
        return when {
            path.startsWith("/recipe/pending") && (method == "POST" || method == "DELETE" || method == "GET") -> recipeApiKey
            path.startsWith("/cleaning-schedule") && (method == "POST" || method == "DELETE") -> cleaningScheduleApiKey
            else -> null
        }
    }
}
