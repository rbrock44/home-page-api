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

internal class BasketballServiceTest {
    @Mock
    lateinit var helper: ScrapingHelperService

    @InjectMocks
    lateinit var service: BasketballService

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
        verify(helper).parseGamesPerDateWebsite(value)
    }

    @Test
    fun `should get the upcoming games`() {
        whenever(helper.parseGamesPerDateWebsite(any(), any())).thenReturn(expected)
        assertEquals(expected, service.getUpcomingGames())
    }
}
