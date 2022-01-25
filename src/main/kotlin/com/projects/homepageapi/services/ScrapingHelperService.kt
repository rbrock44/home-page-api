package com.projects.homepageapi.services

import com.projects.homepageapi.models.Game
import com.projects.homepageapi.models.GamesPerDate
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.io.IOException
import java.net.URLEncoder

@Repository
class ScrapingHelperService(
    @Autowired private val dateService: DateService
) {
    fun getIndexAndDate(
        formattedDate: String,
        tableDates: Elements,
        isMmaFormat: Boolean = false
    ): Pair<Int, String> {
        if (formattedDate != "") {
            tableDates.forEachIndexed { i, element ->
                if (element.text() == formattedDate) {
                    return Pair(i, formattedDate)
                }
            }
            return Pair(-1, formattedDate)
        } else {
            val dateFormat = if (isMmaFormat) DateService.mmaFormat else DateService.nbaFormat
            tableDates.forEachIndexed { i, element ->
                if (dateService.isAfterOrEqualToToday(element.text(), dateFormat)) {
                    return Pair(i, element.text())
                }
            }
            return Pair(-1, "")
        }
    }

    fun getCurrentDate(): String {
        return dateService.getCurrentDate()
    }

    fun errorGamePerDate(message: String): GamesPerDate {
        return GamesPerDate(
            games = emptyList(),
            date = "Error Parsing: $message"
        )
    }

    fun getGameTime(game: Element, otherGameTime: String): String {
        val time = game.getElementsByTag("a")[4].text()
        return time.ifEmpty { otherGameTime }
    }

    fun parseGamesPerDateWebsite(formattedDate: String, isBasketball: Boolean = true): GamesPerDate {
        try {
            val url =
                if (isBasketball) "https://www.espn.com/nba/schedule"
                else "https://www.espn.com/nfl/schedule"
            val doc: Document = Jsoup.connect(url).get()
            val tables: Elements =
                if (isBasketball) Game.getBasketballData(doc)
                else Game.getFootballData(doc)
            val tableDates: Elements = Game.getDates(doc)
            val listOfGames: MutableList<Game> = mutableListOf()

            val pair = getIndexAndDate(
                formattedDate = formattedDate,
                tableDates = tableDates
            )

            val index = pair.first
            val date = if (index == 0) tableDates[index].text() else pair.second

            if (index != -1) {
                if (isBasketball) {
                    val games = tables[index].getElementsByAttribute("data-is-neutral-site")

                    for (game in games) {
                        val opponent: String = Game.getBasketballName(game, 0)
                        val opponentImage: String = Game.getImage(game, 0)
                        val opponentLink: String = Game.getTeamLink(game, 0)
                        val home: String = Game.getBasketballName(game, 1)
                        val homeImage: String = Game.getImage(game, 1)
                        val homeLink: String = Game.getTeamLink(game, 2)

                        //search google for time since espn has a script run over the webpage
                        val search = "$formattedDate $opponent vs $home"
                        val gameUrl = "https://www.google.com/search?q=${URLEncoder.encode(search, "UTF-8")}"
                        val searchDoc: Document = Jsoup.connect(gameUrl).get()
                        val gameTime = Game.getBasketballTime(searchDoc)
                        val time: String = getGameTime(
                            game = game,
                            otherGameTime = gameTime.substring(gameTime.indexOf(",") + 1).trim()
                        )


                        listOfGames.add(
                            Game(
                                opponent = opponent,
                                opponentImageLink = opponentImage,
                                opponentTeamLink = opponentLink,
                                opponentRecord = "",
                                home = home,
                                homeImageLink = homeImage,
                                homeTeamLink = homeLink,
                                homeRecord = "",
                                time = time
                            )
                        )
                    }
                } else {
                    val games = tables[index].getElementsByClass("has-results")

                    for (game in games) {
                        val time: String = this.getGameTime(
                            game = game,
                            otherGameTime = Game.getFootballTime(game)
                        )

                        val opponent: String = Game.getFootballName(game, 0)
                        val opponentImage: String = Game.getFootballImageLink(game, 0)
                        val opponentLink: String = Game.getTeamLink(game, 0)

                        val home: String = Game.getFootballName(game, 1)
                        val homeImage: String = Game.getFootballImageLink(game, 1)
                        val homeLink: String = Game.getTeamLink(game, 2)

                        listOfGames.add(
                            Game(
                                opponent = opponent,
                                opponentImageLink = opponentImage,
                                opponentTeamLink = opponentLink,
                                opponentRecord = "",
                                home = home,
                                homeTeamLink = homeLink,
                                homeImageLink = homeImage,
                                homeRecord = "",
                                time = time,
                            )
                        )
                    }
                }
            }

            return GamesPerDate(
                games = listOfGames,
                date = date
            )

            // In case of any IO errors, we want the messages written to the console
        } catch (e: IOException) {
            return errorGamePerDate(message = e.printStackTrace().toString())
        }
    }
}
