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
    fun `should parse nfl api for upcoming games`() {
        val expected = Constants.nflApiExpected

        whenever(jsoupService.getJson(any())).thenReturn(Constants.nflApiJson)
        assertEquals(expected, helper.parseGamesPerDateWebsite("", false))
        verify(jsoupService).getJson("https://site.api.espn.com/apis/site/v2/sports/football/nfl/scoreboard?dates=20260815-20260905")
    }

    @Test
    fun `should parse nba api for upcoming games`() {
        val expected = Constants.nbaApiExpected

        whenever(jsoupService.getJson(any())).thenReturn(Constants.nbaApiJson)
        assertEquals(expected, helper.parseGamesPerDateWebsite(""))
        verify(jsoupService).getJson("https://site.api.espn.com/apis/site/v2/sports/basketball/nba/scoreboard?dates=20260815-20260905")
    }

    @Test
    fun `should query a specific date when asking for today's games`() {
        whenever(service.today(any())).thenReturn(LocalDate.of(2026, 8, 16))
        whenever(jsoupService.getJson(any())).thenReturn(Constants.nflApiJson)

        helper.parseGamesPerDateWebsite("Sunday, August 16", false)

        verify(jsoupService).getJson("https://site.api.espn.com/apis/site/v2/sports/football/nfl/scoreboard?dates=20260816")
    }

    @Test
    fun `should widen the search window when nothing is found in the near term`() {
        val narrowUrl = "https://site.api.espn.com/apis/site/v2/sports/basketball/nba/scoreboard?dates=20260815-20260905"
        val wideUrl = "https://site.api.espn.com/apis/site/v2/sports/basketball/nba/scoreboard?dates=20260815-20270211"

        whenever(jsoupService.getJson(narrowUrl)).thenReturn("""{"events":[]}""")
        whenever(jsoupService.getJson(wideUrl)).thenReturn(Constants.nbaApiJson)

        val result = helper.parseGamesPerDateWebsite("")

        assertEquals(Constants.nbaApiExpected, result)
        verify(jsoupService).getJson(narrowUrl)
        verify(jsoupService).getJson(wideUrl)
    }

    @Test
    fun `should only return the soonest upcoming date, not stale games from earlier in the response`() {
        whenever(service.today(any())).thenReturn(LocalDate.of(2026, 8, 16))
        whenever(jsoupService.getJson(any())).thenReturn(Constants.nflApiJson)

        val result = helper.parseGamesPerDateWebsite("", false)

        assertEquals(GamesPerDate(games = emptyList(), date = ""), result)
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
