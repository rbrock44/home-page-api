package com.projects.homepageapi.services

import com.projects.homepageapi.models.GamesPerDate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class FootballService(
    @Autowired private val helper: ScrapingHelperService,
) {
    fun getGamesToday(): GamesPerDate {
        return parseWebsite(formattedDate = helper.getCurrentDate())
    }

    fun getUpcomingGames(): GamesPerDate {
        return parseWebsite()
    }

    private fun parseWebsite(formattedDate: String = ""): GamesPerDate {
        return helper.parseGamesPerDateWebsite(
            formattedDate = formattedDate,
            isBasketball = false
        )
    }
}
