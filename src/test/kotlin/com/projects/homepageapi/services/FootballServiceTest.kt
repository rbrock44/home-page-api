package com.projects.homepageapi.services

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

internal class FootballServiceTest {
    @Mock
    lateinit var helper: ScrapingHelperService

    @InjectMocks
    lateinit var service: FootballService

    private var expected = GamesPerDate(emptyList(), "date")

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `should get games for today`() {
        val value = "value"
        whenever(helper.getCurrentDate()).thenReturn(value)
        whenever(helper.parseGamesPerDateWebsite(any(), any())).thenReturn(expected)
        assertEquals(expected, service.getGamesToday())
        verify(helper).parseGamesPerDateWebsite(value, isBasketball = false)
    }

    @Test
    fun `should get the upcoming games`() {
        whenever(helper.parseGamesPerDateWebsite(any(), any())).thenReturn(expected)
        assertEquals(expected, service.getUpcomingGames())
    }

    @Test
    fun `should get the next and previous game days`() {
        whenever(helper.parseAdjacentGameDay(any(), any(), any())).thenReturn(expected)
        assertEquals(expected, service.getNextGames("20261108"))
        assertEquals(expected, service.getPreviousGames("20261108"))
        verify(helper).parseAdjacentGameDay("20261108", true, false)
        verify(helper).parseAdjacentGameDay("20261108", false, false)
    }
}
