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
        // Goldback spot tracks 1/1000 oz gold and doubles for one notes.
        val goldbackPrice = (goldResult.price / 1000.0) * 2.0
        val goldbackDescription = if (goldResult.price < 0) {
            "gold price unavailable"
        } else {
            "derived from gold spot"
        }

        return SpotPrices(
            gold = goldResult.price,
            silver = silverResult.price,
            platinum = platinumResult.price,
            goldback = goldbackPrice,
            description = listOf(
                "Gold: ${if (goldResult.description.isEmpty()) "good" else goldResult.description}",
                "Silver: ${if (silverResult.description.isEmpty()) "good" else silverResult.description}",
                "Platinum: ${if (platinumResult.description.isEmpty()) "good" else platinumResult.description}",
                "Goldback: $goldbackDescription"
            ).joinToString(", ")
        )
    }
}