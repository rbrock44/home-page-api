package com.projects.homepageapi.services

import com.projects.homepageapi.models.GamesPerDate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class BasketballService(
    @Autowired private val helper: ScrapingHelperService
) {
    fun getGamesToday(): GamesPerDate {
        return parseWebsite(formattedDate = helper.getCurrentDate())
    }

    fun getUpcomingGames(): GamesPerDate {
        return parseWebsite()
    }

    fun getNextGames(day: String): GamesPerDate {
        return helper.parseAdjacentGameDay(day = day, forward = true, isBasketball = true)
    }

    fun getPreviousGames(day: String): GamesPerDate {
        return helper.parseAdjacentGameDay(day = day, forward = false, isBasketball = true)
    }

    private fun parseWebsite(formattedDate: String = ""): GamesPerDate {
        return helper.parseGamesPerDateWebsite(
            formattedDate = formattedDate
        )
    }
}
