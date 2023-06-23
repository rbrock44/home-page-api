package com.projects.homepageapi.services

import com.projects.homepageapi.Constants
import com.projects.homepageapi.models.Event
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

    private val date = "Wednesday, November 23, 2022"

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        whenever(service.getCurrentDate()).thenReturn(date)
        whenever(service.isAfterOrEqualToToday(any(), any())).thenReturn(true)
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
    fun `should parse nfl website for games`() {
        val expected = Constants.nflExpected

        whenever(jsoupService.connect(any())).thenReturn(Constants.nflDocument)
        assertEquals(expected, helper.parseGamesPerDateWebsite("", false))
        verify(jsoupService).connect("https://www.espn.com/nfl/schedule")
    }

    @Test
    fun `should parse nba website for games`() {
        val expected = Constants.nbaExpected

        val value = Constants.nbaDocument
        whenever(jsoupService.connect(any())).thenReturn(value)
        assertEquals(expected, helper.parseGamesPerDateWebsite(""))
        verify(jsoupService).connect("https://www.espn.com/nba/schedule")
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
