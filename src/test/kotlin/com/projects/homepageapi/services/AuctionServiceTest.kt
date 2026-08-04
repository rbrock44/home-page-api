package com.projects.homepageapi.services

import com.projects.homepageapi.models.Auction
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class AuctionServiceTest {
    @Mock
    lateinit var helper: ScrapingHelperService

    @InjectMocks
    lateinit var service: AuctionService

    private val expected = listOf(
        Auction(
            service = "service",
            name = "name",
            internetBidding = true,
            url = "url",
            startDate = "start",
            endDate = "end",
            location = "location",
            note = "note"
        )
    )

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `should parse auctions for today's date`() {
        whenever(helper.getCurrentDate()).thenReturn("2026-04-21")
        whenever(helper.parseAuctionWebsites(formattedDate = "2026-04-21")).thenReturn(expected)

        val result = service.getAuctionsToday()

        assertEquals(expected, result)
    }

    @Test
    fun `should parse upcoming auctions without a date filter`() {
        whenever(helper.parseAuctionWebsites(formattedDate = "")).thenReturn(expected)

        val result = service.getUpcomingAuctions()

        assertEquals(expected, result)
        verify(helper, org.mockito.kotlin.never()).getCurrentDate()
    }
}
