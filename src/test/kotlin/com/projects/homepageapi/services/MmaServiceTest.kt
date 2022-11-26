package com.projects.homepageapi.services

import com.projects.homepageapi.models.FightCard
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class MmaServiceTest {
    @Mock
    lateinit var helper: ScrapingHelperService

    @InjectMocks
    lateinit var service: MmaService

    private var expected = FightCard(emptyList(), emptyList(), "date", "title", "link")

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `should get fights for today`() {
        val value = "value"
        whenever(helper.getCurrentDate()).thenReturn(value)
        whenever(helper.parseMmaWebsite(any())).thenReturn(expected)
        assertEquals(expected, service.getFightsToday())
        verify(helper).parseMmaWebsite(value)
    }

    @Test
    fun `should get the upcoming fights`() {
        whenever(helper.parseMmaWebsite(any())).thenReturn(expected)
        assertEquals(expected, service.getUpcomingCard())
    }
}
