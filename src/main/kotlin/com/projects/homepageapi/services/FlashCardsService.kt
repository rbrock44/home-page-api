package com.projects.homepageapi.services

import com.projects.homepageapi.models.FlashCardData
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.springframework.stereotype.Service
import java.net.URL

@Service
class FlashCardsService {
    fun getFlashCardFileFromRepo(): FlashCardData {
        val rawFileUrl = "https://raw.githubusercontent.com/rbrock44/flash-cards-data/master/flash-card-data.json"

        val jsonText = URL(rawFileUrl).openStream().bufferedReader().use { it.readText() }

        val flashCards = Json.decodeFromString<FlashCardData>(jsonText)

        return flashCards
    }
}
