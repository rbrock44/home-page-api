package com.projects.homepageapi.services

import com.projects.homepageapi.Constants
import com.projects.homepageapi.models.FightCard
import com.projects.homepageapi.models.GamesPerDate
import org.jsoup.nodes.Document
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class ScrapingHelperServiceTest {
    @Mock
    lateinit var service: DateService

    @Mock
    lateinit var jsoupService: JsoupService

    @InjectMocks
    lateinit var helper: ScrapingHelperService

    private val date = ""

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        whenever(service.getCurrentDate()).thenReturn(date)
    }

    @Test
    fun `should get current date from date service`() {
        val expected = "value"
        whenever(service.getCurrentDate()).thenReturn(expected)
        assertEquals(expected, helper.getCurrentDate())
    }

    @Test
    fun `should parse mma website for fights`() {
        val expected = FightCard(
            emptyList(),
            emptyList(),
            date,
            "",
            ""
        )

        val value = Constants.mmaDocument as Document
        whenever(jsoupService.connect(any())).thenReturn(value)
        assertEquals(expected, helper.parseMmaWebsite())
        verify(jsoupService).connect("https://www.mmafighting.com/schedule")
    }

    @Test
    fun `should parse nfl website for games`() {
        val expected = GamesPerDate(
            emptyList(),
            date,
        )

        whenever(jsoupService.connect(any())).thenReturn(Constants.nflDocument as Document)
        assertEquals(expected, helper.parseGamesPerDateWebsite("", false))
        verify(jsoupService).connect("https://www.espn.com/nfl/schedule")
    }

    @Test
    fun `should parse nba website for games`() {
        val expected = GamesPerDate(
            emptyList(),
            date,
        )

        val value = Constants.nbaDocument as Document
        whenever(jsoupService.connect(any())).thenReturn(value)
        assertEquals(expected, helper.parseGamesPerDateWebsite(""))
        verify(jsoupService).connect("https://www.espn.com/nba/schedule")
    }
}
