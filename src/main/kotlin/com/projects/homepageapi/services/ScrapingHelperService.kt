package com.projects.homepageapi.services

import com.projects.homepageapi.models.*
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.IOException
import java.net.URLEncoder
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Service
class ScrapingHelperService(
    @Autowired private val dateService: DateService,
    @Autowired private val jsoupService: JsoupService
) {
    fun getCurrentDate(): String {
        return dateService.getCurrentDate()
    }

    fun parseMmaWebsite(formattedDate: String = ""): FightCard {
        val doc: Document = jsoupService.connect("https://www.mmafighting.com/schedule")
        val fightCard: Elements = FightCard.getCards(doc)
        val cardDates: Elements = FightCard.getDates(doc)
        val cardTitles: Elements = FightCard.getTitles(doc)

        val listOfMainFights: MutableList<Fight> = mutableListOf()
        val listOfUnderFights: MutableList<Fight> = mutableListOf()
        var title = ""
        var titleLink = ""

        val pair = getIndexAndDate(
            formattedDate = formattedDate,
            tableDates = cardDates,
            isMmaFormat = true
        )

        val index = pair.first
        val date = if (index == 0) cardDates[index].text() else pair.second

        if (index != -1) {
            title = cardTitles[index].text()
            titleLink = cardTitles[index].getElementsByTag("a").attr("href")

            val card = fightCard[index]
            val mainCard = FightCard.getCard(card, 0).getElementsByTag("li")
            val underCard = FightCard.getCard(card, 1).getElementsByTag("li")

            addFightsToList(listOfMainFights, mainCard)
            addFightsToList(listOfUnderFights, underCard)
        }

        return FightCard(
            main = listOfMainFights,
            under = listOfUnderFights,
            date = date,
            title = title,
            titleLink = titleLink
        )
    }

    fun parseGdqWebsite(): Event {
        val url = "https://gamesdonequick.com/"
        val doc: Document = jsoupService.connect(url)

        val items = doc.select("div div.col-xs-6")
        return if (items.size == 0) {
            val title = doc.getElementsByTag("title").text().trim()
            val dates = "LIVE"
            Event(dates = dates, name = title, url = url)
        } else {
            val names = mutableListOf<String>()
            val dates = mutableListOf<String>()
            for (item in items) {
                val ptags = item.getElementsByTag("p")
                names.add(ptags[0].text())
                dates.add(ptags[1].text())
            }
            Event(dates = dates, name = names, url = url)
        }

    }

    fun parseGamesPerDateWebsite(formattedDate: String, isBasketball: Boolean = true): GamesPerDate {
        try {
            val url =
                if (isBasketball) "https://www.espn.com/nba/schedule"
                else "https://www.espn.com/nfl/schedule"
            val doc: Document = jsoupService.connect(url)
            val tables: Elements =
                if (isBasketball) Game.getBasketballData(doc)
                else Game.getFootballData(doc)
            val tableDates: Elements =
                if (isBasketball) Game.getDates(doc)
                else Game.getFootballDates(doc)
            val listOfGames: MutableList<Game> = mutableListOf()

            val pair = getIndexAndDate(
                formattedDate = formattedDate,
                tableDates = tableDates
            )

            val index = pair.first
            val date = if (index == 0) tableDates[index].text() else pair.second

            if (index != -1) {
                if (isBasketball) {
                    listOfGames.addAll(this.parseBasketball(tables, index, formattedDate))
                } else {
                    listOfGames.addAll(this.parseFootball(tables, index))
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

    private fun getIndexAndDate(
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

    private fun errorGamePerDate(message: String): GamesPerDate {
        return GamesPerDate(
            games = emptyList(),
            date = "Error Parsing: $message"
        )
    }

    private fun getGameTime(game: Element, otherGameTime: String): String {
        val a = game.getElementsByTag("a")
        return if (a.size >= 4) {
            val time = game.getElementsByTag("a")[4].text()
            time.ifEmpty { otherGameTime }
        } else {
            otherGameTime
        }

    }

    private fun shiftGameTimeBack(gameTime: String, hours: Long = 1): String {
        return try {
            val timeFormatter = DateTimeFormatter.ofPattern("h:mm a")
            val localTime = LocalTime.parse(gameTime, timeFormatter)
            val newLocalTime = localTime.minusHours(hours)
            newLocalTime.format(timeFormatter)
        } catch (e: Exception) {
            gameTime
        }
    }

    private fun addFightsToList(list: MutableList<Fight>, card: Elements) {
        for (fight in card) {
            val fightTitle: String = Fight.getTitle(fight)
            val fightLink: String = Fight.getLink(fight)
            val isTitle: Boolean = Fight.isTitleFight(fight)

            list.add(
                Fight(
                    title = fightTitle,
                    link = fightLink,
                    isTitleFight = isTitle
                )
            )
        }
    }

    private fun parseBasketball(tables: Elements, index: Int, formattedDate: String): MutableList<Game> {
        val list = mutableListOf<Game>()
        val games = tables[index].getElementsByClass("Table__TR--sm")

        for (game in games) {
            val opponent: String = Game.getBasketballName(game, 0)
            val home: String = Game.getBasketballName(game, 1)

            //search google for time since espn has a script run over the webpage
            val search = "$formattedDate $opponent vs $home"
            val gameUrl = "https://www.google.com/search?q=${URLEncoder.encode(search, "UTF-8")}"
            val searchDoc: Document = jsoupService.connect(gameUrl)
            val gameTime = Game.getBasketballTime(searchDoc)
            val time: String = getGameTime(
                game = game,
                otherGameTime = gameTime.substring(gameTime.indexOf(",") + 1).trim()
            )

            list.add(
                Game(
                    opponent = opponent,
                    opponentImageLink = Game.getImage(game, 0),
                    opponentTeamLink = Game.getTeamLink(game, 0),
                    opponentRecord = "",
                    home = home,
                    homeImageLink = Game.getImage(game, 1),
                    homeTeamLink = Game.getTeamLink(game, 2),
                    homeRecord = "",
                    time = shiftGameTimeBack(time)
                )
            )
        }

        return list
    }

    private fun parseFootball(tables: Elements, index: Int): MutableList<Game> {
        val list = mutableListOf<Game>()
        val games = tables[index].getElementsByClass("Table__TR--sm")

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

            list.add(
                Game(
                    opponent = opponent,
                    opponentImageLink = opponentImage,
                    opponentTeamLink = opponentLink,
                    opponentRecord = "",
                    home = home,
                    homeTeamLink = homeLink,
                    homeImageLink = homeImage,
                    homeRecord = "",
                    time = shiftGameTimeBack(time),
                )
            )
        }

        return list
    }

    private fun parseAuctions(auctions: Elements, isHibid: Boolean, formattedDate: String): MutableList<Auction> {
        val list = mutableListOf<Auction>()

        for (auction in auctions) {
            val name = if (isHibid) Auction.getHibidName(auction) else Auction.getZipName(auction)
            val service = if (isHibid) Auction.getHibidService(auction) else Auction.getZipService(auction)
            val url = if (isHibid) Auction.getHibidUrl(auction) else Auction.getZipUrl(auction)
            val startDate = if (isHibid) Auction.getHibidStartDate(auction) else Auction.getZipStartDate(auction)
            val endDate = if (isHibid) Auction.getHibidEndDate(auction) else ""
            val location = if (isHibid) Auction.getHibidLocation(auction) else Auction.getZipLocation(auction)
            val note = if (isHibid) Auction.getHibidNote(auction) else ""
            val internetBidding = if (isHibid) Auction.getHibidInternetBidding(auction) else false

            list.add(
                Auction(
                    service = service,
                    name = name,
                    internetBidding = internetBidding,
                    url = url,
                    startDate = startDate,
                    endDate = endDate,
                    location = location,
                    note = note,
                )
            )
        }

        return list
    }

    fun parseAuctionWebsites(formattedDate: String): List<Auction> {
        val listOfAuctions: MutableList<Auction> = mutableListOf()

        val urls = listOf(
            "https://www.auctionzip.com/MO-Auctioneers/65208.html",
            "https://hibid.com/auctions?zip=63701"
        )

        urls.forEach { url ->
            val isHibid = url.containsHibid()

            try {
                val doc: Document = jsoupService.connect(url)

                val auctions: Elements = if (isHibid) Auction.getHibidAuctions(doc) else Auction.getZipAuctions(doc)

                listOfAuctions.addAll(
                    this.parseAuctions(
                        auctions = auctions,
                        isHibid = isHibid,
                        formattedDate = formattedDate
                    ).filter { if (isHibid) it.internetBidding else true }
                )
            } catch (e: IOException) {
                listOfAuctions.add(Auction(
                    service = "Check website for list of auctions",
                    name = "Failed to fetch auctions from: ${url}",
                    internetBidding = false,
                    url = url,
                    startDate = "",
                    endDate = "",
                    location = "",
                    note = "",
                ))
                println(e.message)
            }
        }

        return listOfAuctions
    }

    fun String.containsHibid(): Boolean {
        return this.contains("hibid", ignoreCase = true)
    }

    fun parseGoldWebsite(): Double  {
        val url = "https://www.investing.com/currencies/xau-usd"

        return parsePreciousMetalWebsite(url)
    }

    fun parseSilverWebsite(): Double  {
        val url = "https://www.investing.com/currencies/xag-usd"

        return parsePreciousMetalWebsite(url)
    }

    fun parsePreciousMetalWebsite(url: String): Double {
        return try {
            val doc: Document = Jsoup.connect(url).get()

            val value: String? = SpotPrices.getElement(doc)
            value?.toDoubleOrNull() ?: -1.0
        } catch (e: IOException) {
            println("IOException: ${e.message}")
            -1.0
        } catch (e: Exception) {
            println("Error parsing value: ${e.message}")
            -1.0
        }
    }
}
