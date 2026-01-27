package com.projects.homepageapi.services

import com.projects.homepageapi.models.SpotPrices
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SpotPriceService(
    @Autowired private val helper: ScrapingHelperService
) {
    fun getSpotPrices(): SpotPrices {
        val goldResult = helper.parseGoldWebsite()
        val silverResult = helper.parseSilverWebsite()
        return SpotPrices(
            gold = goldResult.price,
            silver = silverResult.price,
            description = listOf(
                "Gold: ${if (goldResult.description.isEmpty()) "good" else goldResult.description}",
                "Silver: ${if (silverResult.description.isEmpty()) "good" else silverResult.description}"
            ).joinToString(", ")
        )
    }
}