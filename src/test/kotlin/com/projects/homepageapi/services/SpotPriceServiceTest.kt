package com.projects.homepageapi.services

import com.projects.homepageapi.models.PreciousMetalResult
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

internal class SpotPriceServiceTest {
    @Mock
    lateinit var helper: ScrapingHelperService

    @InjectMocks
    lateinit var service: SpotPriceService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `should derive goldback price as 1 1000th of gold doubled`() {
        whenever(helper.parseGoldWebsite()).thenReturn(PreciousMetalResult(price = 2000.0))
        whenever(helper.parseSilverWebsite()).thenReturn(PreciousMetalResult(price = 25.0))
        whenever(helper.parsePlatinumWebsite()).thenReturn(PreciousMetalResult(price = 1000.0))

        val result = service.getSpotPrices()

        assertEquals(2000.0, result.gold)
        assertEquals(25.0, result.silver)
        assertEquals(1000.0, result.platinum)
        assertEquals(4.0, result.goldback)
    }

    @Test
    fun `should note gold price unavailable in description when gold price is negative`() {
        whenever(helper.parseGoldWebsite()).thenReturn(PreciousMetalResult(price = -1.0))
        whenever(helper.parseSilverWebsite()).thenReturn(PreciousMetalResult(price = 25.0))
        whenever(helper.parsePlatinumWebsite()).thenReturn(PreciousMetalResult(price = 1000.0))

        val result = service.getSpotPrices()

        assertTrue(result.description.contains("Goldback: gold price unavailable"))
    }

    @Test
    fun `should surface each metal's scraping description when present`() {
        whenever(helper.parseGoldWebsite()).thenReturn(PreciousMetalResult(price = 2000.0, description = "stale"))
        whenever(helper.parseSilverWebsite()).thenReturn(PreciousMetalResult(price = 25.0))
        whenever(helper.parsePlatinumWebsite()).thenReturn(PreciousMetalResult(price = 1000.0))

        val result = service.getSpotPrices()

        assertEquals("Gold: stale, Silver: good, Platinum: good, Goldback: derived from gold spot", result.description)
    }
}
