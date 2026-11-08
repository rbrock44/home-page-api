package com.projects.homepageapi.controllers

import com.projects.homepageapi.*
import com.projects.homepageapi.models.GamesPerDate
import com.projects.homepageapi.services.BasketballService
import com.projects.homepageapi.services.FootballService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("games-per-date")
@CrossOrigin(
    originPatterns = [
        angularOrigin,
        wildcardOrigin,
        cleaningScheduleOrigin,
        flashCardsOrigin,
        homePageOrigin,
        utilitiesOrigin,
    ],
    maxAge = maxAge
)
class GamesPerDateController(
    @Autowired private val basketballService: BasketballService,
    @Autowired private val footballService: FootballService
) {
    @GetMapping("/basketball/today")
    fun getBasketballGamesToday(): GamesPerDate {
        return basketballService.getGamesToday()
    }

    @GetMapping("/basketball/upcoming")
    fun getBasketballGamesUpcoming(): GamesPerDate {
        return basketballService.getUpcomingGames()
    }

    @GetMapping("/basketball/next")
    fun getBasketballGamesNext(@RequestParam day: String): GamesPerDate {
        return basketballService.getNextGames(day)
    }

    @GetMapping("/basketball/previous")
    fun getBasketballGamesPrevious(@RequestParam day: String): GamesPerDate {
        return basketballService.getPreviousGames(day)
    }

    @GetMapping("/football/today")
    fun getFootballGamesToday(): GamesPerDate {
        return footballService.getGamesToday()
    }

    @GetMapping("/football/upcoming")
    fun getFootballGamesUpcoming(): GamesPerDate {
        return footballService.getUpcomingGames()
    }

    @GetMapping("/football/next")
    fun getFootballGamesNext(@RequestParam day: String): GamesPerDate {
        return footballService.getNextGames(day)
    }

    @GetMapping("/football/previous")
    fun getFootballGamesPrevious(@RequestParam day: String): GamesPerDate {
        return footballService.getPreviousGames(day)
    }
}
