package com.projects.homepageapi.services

import com.projects.homepageapi.Constants
import com.projects.homepageapi.models.Event
import com.projects.homepageapi.models.GamesPerDate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.time.LocalDate
import java.io.File
import java.io.IOException
import org.jsoup.Jsoup

internal class ScrapingHelperServiceTest {
    @Mock
    lateinit var service: DateService

    @Mock
    lateinit var jsoupService: JsoupService

    @InjectMocks
    lateinit var helper: ScrapingHelperService

    private val date = "Wednesday, November 23, 2022"

    private val fixtureToday = LocalDate.of(2026, 8, 15)

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        whenever(service.getCurrentDate()).thenReturn(date)
        whenever(service.isAfterOrEqualToToday(any(), any())).thenReturn(true)
        whenever(service.today(any())).thenReturn(fixtureToday)
    }

    @Test
    fun `should get current date from date service`() {
        val expected = "value"
        whenever(service.getCurrentDate()).thenReturn(expected)
        assertEquals(expected, helper.getCurrentDate())
    }

    @Test
    fun `should parse mma website for fights`() {
        val expected = Constants.mmaExpected
        val value = Constants.mmaDocument
        whenever(jsoupService.connect(any())).thenReturn(value)
        assertEquals(expected, helper.parseMmaWebsite())
        verify(jsoupService).connect("https://www.mmafighting.com/schedule")
    }

    @Test
    fun `should parse mma2 website for fights`() {
        val expected = Constants.mma2Expected
        val value = Constants.mma2Document
        whenever(jsoupService.connect(any())).thenReturn(value)
        assertEquals(expected, helper.parseMmaWebsite())
        verify(jsoupService).connect("https://www.mmafighting.com/schedule")
    }

    @Test
    fun `should list recent results and upcoming mma cards oldest first`() {
        whenever(jsoupService.connect("https://www.mmafighting.com/fight-results"))
            .thenReturn(Jsoup.parse(File("src/test/resources/mma-results.html").readText()))
        whenever(jsoupService.connect("https://www.mmafighting.com/schedule"))
            .thenReturn(Jsoup.parse(File("src/test/resources/mma-schedule.html").readText()))

        val cards = helper.parseAllMmaCards()

        // 3 results + 6 scheduled, minus UFC 336 which has no fights announced yet
        assertEquals(
            listOf(
                "UFC 333: Volkanovski vs. Evloev",
                "UFC Vegas 123: Moicano vs. Nolan",
                "UFC Vegas 124: Cortes-Acosta vs. Walker",
                "UFC 334: Gane vs. Hokit",
                "UFC Qatar: Prochazka vs. Stirling",
                "UFC Saudi Arabia: Adesanya vs. Magomedov",
                "UFC 335: Oliveira vs. Lopes",
                "UFC Sydney: Salkilld vs. Saint Denis"
            ),
            cards.map { it.title }
        )
        val volkanovski = cards.first()
        assertEquals("October 24, 2026", volkanovski.date)
        assertEquals("https://www.mmafighting.com/fight-card/505144/ufc-333-volkanovski-vs-evloev", volkanovski.titleLink)
        assertEquals("Alexander Volkanovski vs Movsar Evloev", volkanovski.main.first().title)
        assertEquals(true, volkanovski.main.first().isTitleFight)
        assertEquals(14, volkanovski.main.size + volkanovski.under.size)
    }

    @Test
    fun `should still list upcoming mma cards when results fail to load`() {
        whenever(jsoupService.connect("https://www.mmafighting.com/fight-results")).thenAnswer { throw IOException("down") }
        whenever(jsoupService.connect("https://www.mmafighting.com/schedule"))
            .thenReturn(Jsoup.parse(File("src/test/resources/mma-schedule.html").readText()))

        assertEquals(5, helper.parseAllMmaCards().size)
    }

    @Test
    fun `should not repeat an mma card listed on both pages`() {
        val results = Jsoup.parse(File("src/test/resources/mma-results.html").readText())
        whenever(jsoupService.connect(any())).thenReturn(results)

        assertEquals(3, helper.parseAllMmaCards().size)
    }

    @Test
    fun `should parse nfl api for upcoming games`() {
        val expected = Constants.nflApiExpected

        whenever(jsoupService.getJson(any())).thenReturn(Constants.nflApiJson)
        assertEquals(expected, helper.parseGamesPerDateWebsite("", false))
        verify(jsoupService).getJson("https://site.api.espn.com/apis/site/v2/sports/football/nfl/scoreboard?dates=202608")
    }

    @Test
    fun `should parse nba api for upcoming games`() {
        val expected = Constants.nbaApiExpected

        whenever(jsoupService.getJson(any())).thenReturn(Constants.nbaApiJson)
        assertEquals(expected, helper.parseGamesPerDateWebsite(""))
        verify(jsoupService).getJson("https://site.api.espn.com/apis/site/v2/sports/basketball/nba/scoreboard?dates=202608")
    }

    @Test
    fun `should query a specific date when asking for today's games`() {
        whenever(service.today(any())).thenReturn(LocalDate.of(2026, 8, 16))
        whenever(jsoupService.getJson(any())).thenReturn(Constants.nflApiJson)

        helper.parseGamesPerDateWebsite("Sunday, August 16", false)

        verify(jsoupService).getJson("https://site.api.espn.com/apis/site/v2/sports/football/nfl/scoreboard?dates=20260816")
    }

    @Test
    fun `should search the next month when nothing is found in the current month`() {
        val currentMonthUrl = "https://site.api.espn.com/apis/site/v2/sports/basketball/nba/scoreboard?dates=202608"
        val nextMonthUrl = "https://site.api.espn.com/apis/site/v2/sports/basketball/nba/scoreboard?dates=202609"

        whenever(jsoupService.getJson(currentMonthUrl)).thenReturn("""{"events":[]}""")
        whenever(jsoupService.getJson(nextMonthUrl)).thenReturn(Constants.nbaApiJson)

        val result = helper.parseGamesPerDateWebsite("")

        assertEquals(Constants.nbaApiExpected, result)
        verify(jsoupService).getJson(currentMonthUrl)
        verify(jsoupService).getJson(nextMonthUrl)
    }

    @Test
    fun `should only return the soonest upcoming date, not stale games from earlier in the response`() {
        whenever(service.today(any())).thenReturn(LocalDate.of(2026, 8, 16))
        whenever(jsoupService.getJson(any())).thenReturn(Constants.nflApiJson)

        val result = helper.parseGamesPerDateWebsite("", false)

        assertEquals(GamesPerDate(games = emptyList(), date = ""), result)
    }

    @Test
    fun `should return the next game day after the given day`() {
        whenever(jsoupService.getJson(any())).thenReturn(Constants.nflApiJson)

        val result = helper.parseAdjacentGameDay("20260814", forward = true, isBasketball = false)

        assertEquals(Constants.nflApiExpected, result)
        verify(jsoupService).getJson("https://site.api.espn.com/apis/site/v2/sports/football/nfl/scoreboard?dates=202608")
    }

    @Test
    fun `should return the previous game day before the given day`() {
        whenever(jsoupService.getJson(any())).thenReturn(Constants.nflApiJson)

        val result = helper.parseAdjacentGameDay("20260815", forward = false, isBasketball = false)

        assertEquals("20260814", result.day)
        assertEquals("Friday, August 14, 2026 - Preseason", result.date)
    }

    @Test
    fun `should search the previous month when nothing is found before the given day`() {
        val currentMonthUrl = "https://site.api.espn.com/apis/site/v2/sports/basketball/nba/scoreboard?dates=202611"
        val previousMonthUrl = "https://site.api.espn.com/apis/site/v2/sports/basketball/nba/scoreboard?dates=202610"

        whenever(jsoupService.getJson(currentMonthUrl)).thenReturn("""{"events":[]}""")
        whenever(jsoupService.getJson(previousMonthUrl)).thenReturn(Constants.nbaApiJson)

        val result = helper.parseAdjacentGameDay("20261105", forward = false)

        assertEquals(Constants.nbaApiExpected, result)
    }

    @Test
    fun `should return an error for a malformed day`() {
        val result = helper.parseAdjacentGameDay("not-a-day", forward = true)

        assertEquals(emptyList<Any>(), result.games)
        assertEquals("Error Parsing: invalid day 'not-a-day', expected yyyyMMdd", result.date)
    }

    @Test
    fun `should parse gdq website for dates`() {
        val expected = Event(
            name = listOf("Winter", "Summer"),
            dates = listOf("2023: January 8-15", "2024"),
            url = "https://gamesdonequick.com/"
        )

        val value = Constants.gdqDocument
        whenever(jsoupService.connect(any())).thenReturn(value)
        assertEquals(expected, helper.parseGdqWebsite())
        verify(jsoupService).connect("https://gamesdonequick.com/")
    }
}
