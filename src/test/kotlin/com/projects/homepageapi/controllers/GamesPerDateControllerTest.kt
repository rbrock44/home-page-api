package com.projects.homepageapi.controllers

import com.projects.homepageapi.models.GamesPerDate
import com.projects.homepageapi.services.BasketballService
import com.projects.homepageapi.services.FootballService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

internal class GamesPerDateControllerTest {
    @Mock
    lateinit var footballService: FootballService

    @Mock
    lateinit var basketballService: BasketballService

    @InjectMocks
    lateinit var controller: GamesPerDateController

    private val expected = GamesPerDate(emptyList(), "date")

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `should get all basketball games for today`() {
        whenever(basketballService.getGamesToday()).thenReturn(expected)
        kotlin.test.assertEquals(expected, controller.getBasketballGamesToday())
    }

    @Test
    fun `should get all upcoming basketball games`() {
        whenever(basketballService.getUpcomingGames()).thenReturn(expected)
        kotlin.test.assertEquals(expected, controller.getBasketballGamesUpcoming())
    }

    @Test
    fun `should get all football games for today`() {
        whenever(footballService.getGamesToday()).thenReturn(expected)
        kotlin.test.assertEquals(expected, controller.getFootballGamesToday())
    }

    @Test
    fun `should get all upcoming football games`() {
        whenever(footballService.getUpcomingGames()).thenReturn(expected)
        kotlin.test.assertEquals(expected, controller.getFootballGamesUpcoming())
    }

    @Test
    fun `should get the next and previous basketball game days`() {
        whenever(basketballService.getNextGames("20261108")).thenReturn(expected)
        whenever(basketballService.getPreviousGames("20261108")).thenReturn(expected)
        kotlin.test.assertEquals(expected, controller.getBasketballGamesNext("20261108"))
        kotlin.test.assertEquals(expected, controller.getBasketballGamesPrevious("20261108"))
    }

    @Test
    fun `should get the next and previous football game days`() {
        whenever(footballService.getNextGames("20261108")).thenReturn(expected)
        whenever(footballService.getPreviousGames("20261108")).thenReturn(expected)
        kotlin.test.assertEquals(expected, controller.getFootballGamesNext("20261108"))
        kotlin.test.assertEquals(expected, controller.getFootballGamesPrevious("20261108"))
    }
}
