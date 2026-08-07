package com.projects.homepageapi.services

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration

@Service
class GithubDispatchService(
    @Value("\${github.dispatch.token:}") private val token: String,
    @Value("\${github.dispatch.repo:rbrock44/family-recipes}") private val repo: String
) {
    private val client = HttpClient.newBuilder()
        .connectTimeout(Duration.ofSeconds(5))
        .build()

    fun dispatchRecipeSubmitted() {
        if (token.isBlank()) {
            println("github.dispatch.token not set; skipping repository_dispatch")
            return
        }

        try {
            val request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.github.com/repos/$repo/dispatches"))
                .header("Accept", "application/vnd.github+json")
                .header("Authorization", "Bearer $token")
                .header("Content-Type", "application/json")
                .timeout(Duration.ofSeconds(5))
                .POST(HttpRequest.BodyPublishers.ofString("""{"event_type":"recipe-submitted"}"""))
                .build()

            val response = client.send(request, HttpResponse.BodyHandlers.ofString())
            if (response.statusCode() != 204) {
                println("GitHub dispatch failed: ${response.statusCode()} ${response.body()}")
            }
        } catch (e: Exception) {
            println("GitHub dispatch error: ${e.message}")
        }
    }
}
