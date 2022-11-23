package com.projects.homepageapi.controllers

import com.projects.homepageapi.models.FightCard
import com.projects.homepageapi.services.MmaService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

internal class FightCardControllerTest {
    @Mock
    lateinit var service: MmaService

    @InjectMocks
    lateinit var controller: FightCardController

    private val expected = FightCard(emptyList(), emptyList(), "date", "Title", "link")

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `should get all fights for today`() {
        whenever(service.getFightsToday()).thenReturn(expected)
        assertEquals(expected, controller.getFightsToday())
    }

    @Test
    fun `should get all upcoming fights`() {
        whenever(service.getUpcomingCard()).thenReturn(expected)
        assertEquals(expected, controller.getUpcomingCard())
    }
}
