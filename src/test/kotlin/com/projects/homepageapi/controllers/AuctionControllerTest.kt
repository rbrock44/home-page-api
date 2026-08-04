package com.projects.homepageapi.controllers

import com.projects.homepageapi.models.Auction
import com.projects.homepageapi.services.AuctionService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

internal class AuctionControllerTest {
    @Mock
    lateinit var auctionService: AuctionService

    @InjectMocks
    lateinit var controller: AuctionController

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
    fun `should get today's auctions`() {
        whenever(auctionService.getAuctionsToday()).thenReturn(expected)
        assertEquals(expected, controller.getAuctionsToday())
    }

    @Test
    fun `should get upcoming auctions`() {
        whenever(auctionService.getUpcomingAuctions()).thenReturn(expected)
        assertEquals(expected, controller.getAuctionsUpcoming())
    }
}
