package com.projects.homepageapi.services

import com.projects.homepageapi.*
import com.projects.homepageapi.models.*
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.IOException
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale

private const val UPCOMING_MONTHS_TO_SEARCH = 6L
private val YYYY_MM_DD: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")
private const val MMA_SCHEDULE_URL = "https://www.mmafighting.com/schedule"
private const val MMA_RESULTS_URL = "https://www.mmafighting.com/fight-results"

@Service
class ScrapingHelperService(
    @Autowired private val dateService: DateService,
    @Autowired private val jsoupService: JsoupService
) {
    fun getCurrentDate(): String {
        return dateService.getCurrentDate()
    }

    fun parseMmaWebsite(formattedDate: String = ""): FightCard {
        val doc: Document = jsoupService.connect(MMA_SCHEDULE_URL)
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
        val date = if (index in cardDates.indices) cardDates[index].text() else pair.second

        if (index in fightCard.indices && index in cardTitles.indices) {
            title = cardTitles[index].text()
            titleLink = cardTitles[index].getElementsByTag("a").attr("href")

            val card = fightCard[index]
            val splitCards = card.getElementsByClass("m-mmaf-pte-event-list__split-item")
            val mainCard = if (splitCards.isNotEmpty()) splitCards[0].getElementsByTag("li") else Elements()
            val underCard = if (splitCards.size > 1) splitCards[1].getElementsByTag("li") else Elements()

            addFightsToList(listOfMainFights, mainCard)
            addFightsToList(listOfUnderFights, underCard)
        }

        if (listOfMainFights.isEmpty() && listOfUnderFights.isEmpty()) {
            parseMmaWebsiteFromNextData(doc, formattedDate)?.let { return it }
        }

        return FightCard(
            main = listOfMainFights,
            under = listOfUnderFights,
            date = date,
            title = title,
            titleLink = titleLink
        )
    }

    private fun parseMmaWebsiteFromNextData(doc: Document, formattedDate: String): FightCard? {
        val targetEvent = getMmaEvents(doc).firstOrNull { event ->
            formattedDate.isEmpty() || formatMmaEventDate(event.path("eventStartAt").asText()) == formattedDate
        } ?: return null

        return toFightCard(targetEvent)
    }

    // Recent results plus every upcoming card, oldest first, so the widget can page between them.
    // Cards with no fights announced yet are left out, matching how the widget hides empty cards.
    fun parseAllMmaCards(): List<FightCard> {
        val events = listOf(MMA_RESULTS_URL, MMA_SCHEDULE_URL).flatMap { url ->
            try {
                getMmaEvents(jsoupService.connect(url))
            } catch (e: IOException) {
                emptyList()
            }
        }

        return events
            .filter { it.path("mmaFights").path("nodes").size() > 0 }
            .distinctBy { it.path("permalink").asText() }
            .sortedBy { event ->
                try {
                    OffsetDateTime.parse(event.path("eventStartAt").asText()).toInstant()
                } catch (e: Exception) {
                    java.time.Instant.MAX
                }
            }
            .map { toFightCard(it) }
    }

    private fun getMmaEvents(doc: Document): List<JsonNode> {
        val nextData = doc.getElementById("__NEXT_DATA__")?.data() ?: return emptyList()
        val responses = ObjectMapper().readTree(nextData)
            .path("props").path("pageProps").path("hydration").path("responses")

        val scheduleResponse = responses.firstOrNull {
            it.path("operationName").asText() == "MMAScheduleResultsLayoutQuery"
        } ?: return emptyList()

        val events = scheduleResponse.path("data").path("mmaEvents").path("nodes")
        return if (events.isArray) events.toList() else emptyList()
    }

    private fun toFightCard(event: JsonNode): FightCard {
        val main = mutableListOf<Fight>()
        val under = mutableListOf<Fight>()
        for (fight in event.path("mmaFights").path("nodes")) {
            val parsedFight = Fight(
                title = fight.path("title").asText(),
                link = fight.path("permalink").asText(),
                isTitleFight = fight.path("titleFight").asBoolean(false)
            )
            if (fight.path("mainCard").asBoolean(false)) {
                main.add(parsedFight)
            } else {
                under.add(parsedFight)
            }
        }

        return FightCard(
            main = main,
            under = under,
            date = formatMmaEventDate(event.path("eventStartAt").asText()),
            title = event.path("title").asText(),
            titleLink = event.path("permalink").asText()
        )
    }

    private fun formatMmaEventDate(value: String): String {
        return try {
            OffsetDateTime.parse(value)
                .format(DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH))
        } catch (e: Exception) {
            value
        }
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
        return try {
            val baseUrl = scoreboardUrl(isBasketball)
            val today = todayEastern()

            if (formattedDate.isNotEmpty()) {
                val url = "$baseUrl?dates=${today.format(YYYY_MM_DD)}"
                return parseGamesFromApiJson(json = jsoupService.getJson(url), fallbackDate = formattedDate) { dates ->
                    dates.filter { !it.isBefore(today) }.minOrNull()
                }
            }

            // ESPN's default (no `dates`) response is tied to the current week and can be
            // entirely in the past once it's played out, and date ranges are rejected - so query
            // month by month, starting with the current one, until upcoming games turn up. The
            // month cap covers long off-season gaps (e.g. NBA's summer break before preseason).
            searchByMonth(baseUrl, today, forward = true, fallbackDate = formattedDate) { dates ->
                dates.filter { !it.isBefore(today) }.minOrNull()
            }
        } catch (e: IOException) {
            errorGamePerDate(message = e.message ?: "")
        }
    }

    // The nearest game day strictly after (forward) or before `day` (yyyyMMdd), searching
    // month by month so off days and off-season gaps are skipped over.
    fun parseAdjacentGameDay(day: String, forward: Boolean, isBasketball: Boolean = true): GamesPerDate {
        val anchor = try {
            LocalDate.parse(day, YYYY_MM_DD)
        } catch (e: DateTimeParseException) {
            return errorGamePerDate(message = "invalid day '$day', expected yyyyMMdd")
        }

        return try {
            searchByMonth(scoreboardUrl(isBasketball), anchor, forward, fallbackDate = "") { dates ->
                if (forward) dates.filter { it.isAfter(anchor) }.minOrNull()
                else dates.filter { it.isBefore(anchor) }.maxOrNull()
            }
        } catch (e: IOException) {
            errorGamePerDate(message = e.message ?: "")
        }
    }

    private fun scoreboardUrl(isBasketball: Boolean): String {
        val sport = if (isBasketball) "basketball" else "football"
        val league = if (isBasketball) "nba" else "nfl"
        return "https://site.api.espn.com/apis/site/v2/sports/$sport/$league/scoreboard"
    }

    private fun searchByMonth(
        baseUrl: String,
        start: LocalDate,
        forward: Boolean,
        fallbackDate: String,
        pickDate: (List<LocalDate>) -> LocalDate?
    ): GamesPerDate {
        val yyyyMM = DateTimeFormatter.ofPattern("yyyyMM")
        for (monthOffset in 0L until UPCOMING_MONTHS_TO_SEARCH) {
            val month = if (forward) start.plusMonths(monthOffset) else start.minusMonths(monthOffset)
            val url = "$baseUrl?dates=${month.format(yyyyMM)}"
            val result = parseGamesFromApiJson(json = jsoupService.getJson(url), fallbackDate = fallbackDate, pickDate = pickDate)
            if (result.games.isNotEmpty()) return result
        }

        return GamesPerDate(games = emptyList(), date = fallbackDate)
    }

    // ESPN responses can span many days (a week by default, a month with `dates=yyyyMM`) -
    // `pickDate` chooses which single Eastern day's games to return.
    private fun parseGamesFromApiJson(
        json: String,
        fallbackDate: String,
        pickDate: (List<LocalDate>) -> LocalDate?
    ): GamesPerDate {
        val events = ObjectMapper().readTree(json).path("events")

        val eventsWithDate = mutableListOf<Pair<JsonNode, LocalDate>>()
        val eventIterator = events.elements()
        while (eventIterator.hasNext()) {
            val event = eventIterator.next()
            val eventDate = toEasternDate(event.path("date").asText()) ?: continue
            eventsWithDate.add(event to eventDate)
        }

        val targetDate = pickDate(eventsWithDate.map { it.second }.distinct())
            ?: return GamesPerDate(games = emptyList(), date = fallbackDate)

        val games = mutableListOf<Game>()
        for ((event, eventDate) in eventsWithDate) {
            if (eventDate != targetDate) continue

            val competitors = event.path("competitions").path(0).path("competitors")

            var home: JsonNode? = null
            var away: JsonNode? = null
            val competitorIterator = competitors.elements()
            while (competitorIterator.hasNext()) {
                val competitor = competitorIterator.next()
                when (competitor.path("homeAway").asText()) {
                    "home" -> home = competitor
                    "away" -> away = competitor
                }
            }
            if (home == null || away == null) continue

            games.add(
                Game(
                    opponent = away.path("team").path("displayName").asText(),
                    opponentImageLink = away.path("team").path("logo").asText(),
                    opponentTeamLink = away.path("team").path("links").path(0).path("href").asText(),
                    opponentRecord = "",
                    home = home.path("team").path("displayName").asText(),
                    homeImageLink = home.path("team").path("logo").asText(),
                    homeTeamLink = home.path("team").path("links").path(0).path("href").asText(),
                    homeRecord = "",
                    time = getApiGameTime(event.path("competitions").path(0), home, away)
                )
            )
        }

        val firstEvent = eventsWithDate.first { it.second == targetDate }.first
        val date = formatEventDate(firstEvent.path("date").asText()) +
            getSeasonSuffix(firstEvent.path("season").path("slug").asText())

        return GamesPerDate(games = games, date = date, day = targetDate.format(YYYY_MM_DD))
    }

    private fun todayEastern(): LocalDate {
        return dateService.today(ZoneId.of("America/New_York"))
    }

    private fun toEasternDate(isoDate: String): LocalDate? {
        return try {
            OffsetDateTime.parse(isoDate).atZoneSameInstant(ZoneId.of("America/New_York")).toLocalDate()
        } catch (e: Exception) {
            null
        }
    }

    private fun getApiGameTime(competition: JsonNode, home: JsonNode, away: JsonNode): String {
        val statusType = competition.path("status").path("type")
        return when {
            statusType.path("completed").asBoolean(false) ->
                "${away.path("team").path("abbreviation").asText()} ${away.path("score").asText()}, " +
                    "${home.path("team").path("abbreviation").asText()} ${home.path("score").asText()}"
            statusType.path("state").asText() == "pre" ->
                competition.path("date").asText()
            else ->
                statusType.path("shortDetail").asText().replaceFirst(Regex("^\\d{1,2}/\\d{1,2} - "), "")
        }
    }

    private fun formatEventDate(isoDate: String): String {
        return try {
            OffsetDateTime.parse(isoDate).toInstant()
                .atZone(ZoneId.of("America/New_York"))
                .format(DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy", Locale.ENGLISH))
        } catch (e: Exception) {
            isoDate
        }
    }

    private fun getSeasonSuffix(slug: String): String {
        return when (slug) {
            "preseason" -> " - Preseason"
            "postseason" -> " - Postseason"
            else -> ""
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

    private fun parseAuctions(auctions: Elements, isHibid: Boolean): MutableList<Auction> {
        val list = mutableListOf<Auction>()

        for (auction in auctions) {
            val service = if (isHibid) Auction.getHibidService(auction) else Auction.getZipService(auction)
            val url = if (isHibid) Auction.getHibidUrl(auction) else Auction.getZipUrl(auction)
            val startDate = if (isHibid) Auction.getHibidStartDate(auction) else Auction.getZipStartDate(auction)
            val endDate = if (isHibid) Auction.getHibidEndDate(auction) else ""
            val location = if (isHibid) Auction.getHibidLocation(auction) else Auction.getZipLocation(auction)
            val rawName = if (isHibid) Auction.getHibidName(auction) else Auction.getZipName(auction)
            val name = Auction.getDisplayName(name = rawName, service = service, location = location)
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
                        isHibid = isHibid
                    ).filter { if (isHibid) it.internetBidding else true }
                )
            } catch (e: Exception) {
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

    fun parseGoldWebsite(): PreciousMetalResult  {
        return parsePreciousMetalWebsite(goldSpotOrigin)
    }

    fun parseSilverWebsite(): PreciousMetalResult  {
        return parsePreciousMetalWebsite(silverSpotOrigin)
    }

    fun parsePlatinumWebsite(): PreciousMetalResult {
        return parsePreciousMetalWebsite(platinumSpotOrigin)
    }

    fun parsePalladiumWebsite(): PreciousMetalResult {
        return parsePreciousMetalWebsite(palladiumSpotOrigin)
    }

    fun parsePreciousMetalWebsite(url: String): PreciousMetalResult {
        var price = -1.0
        var description = ""
        
        try {
            val doc: Document = jsoupService.connect(url)
            val value: String? = SpotPrices.getElement(doc)
            val parsedPrice = value?.replace(",", "")?.toDoubleOrNull()
            
            if (parsedPrice != null) {
                price = parsedPrice
            } else {
                description = "value is null"
            }
        } catch (e: IOException) {
            println("IOException: ${e.message}")
            description = "IOException: ${e.message}"
        } catch (e: Exception) {
            println("Error parsing value: ${e.message}")
            description = "Error parsing value: ${e.message}"
        }
        
        return PreciousMetalResult(price, description)
    }
}
