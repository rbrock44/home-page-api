package com.projects.homepageapi.configs

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

@Component
class ApiKeyInterceptor(
    @Value("\${api.key:}") private val apiKey: String
) : HandlerInterceptor {
    
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val requestPath = request.requestURI
        
        // Check if the request is for a secure endpoint
        if (isSecureEndpoint(requestPath, request.method)) {
            val providedKey = request.getHeader("X-API-Key")
            
            if (apiKey.isEmpty() || providedKey != apiKey) {
                response.status = HttpServletResponse.SC_UNAUTHORIZED
                response.contentType = "application/json"
                response.writer.write("""{"error":"Unauthorized - Invalid or missing API key"}""")
                return false
            }
        }
        return true
    }
    
    private fun isSecureEndpoint(path: String, method: String): Boolean {
        return (path.startsWith("/recipe/pending") && (method == "POST" || method == "DELETE"))
    }
}
