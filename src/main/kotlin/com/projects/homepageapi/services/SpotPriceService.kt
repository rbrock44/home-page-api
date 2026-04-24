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
        val platinumResult = helper.parsePlatinumWebsite()
        val goldbackResult = helper.parseGoldbackWebsite()
        return SpotPrices(
            gold = goldResult.price,
            silver = silverResult.price,
            platinum = platinumResult.price,
            goldback = goldbackResult.price,
            description = listOf(
                "Gold: ${if (goldResult.description.isEmpty()) "good" else goldResult.description}",
                "Silver: ${if (silverResult.description.isEmpty()) "good" else silverResult.description}",
                "Platinum: ${if (platinumResult.description.isEmpty()) "good" else platinumResult.description}",
                "Goldback: ${if (goldbackResult.description.isEmpty()) "good" else goldbackResult.description}"
            ).joinToString(", ")
        )
    }
}