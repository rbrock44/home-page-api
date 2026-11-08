package com.projects.homepageapi.models

import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AuctionTest {
    private fun zipListing(): Element {
        val html = """
            <div class="az-ListOfLlisting__Container">
              <a class="az_title_href" href="/auction/1">Auction</a>
              <a class="az_title_href" href="/auction/1#photos">View Photo Gallery</a>
              <a class="az_title_href" href="/auction/1#photos">View Photo Gallery</a>
              <div class="az-ListOfLlisting__company--title">Brucker Auction Service</div>
              <div class="az-ListOfLlisting__company--title">Brucker Auction Service</div>
            </div>
        """.trimIndent()
        return Jsoup.parse(html).getElementsByClass("az-ListOfLlisting__Container").first()!!
    }

    @Test
    fun `zip name should not repeat or include photo gallery links`() {
        assertEquals("Auction", Auction.getZipName(zipListing()))
    }

    @Test
    fun `zip name should strip photo gallery text inside the title link`() {
        val element = Jsoup.parse(
            """<div><a class="az_title_href" href="/a">Estate Sale View Photo Gallery</a></div>"""
        ).body().child(0)
        assertEquals("Estate Sale", Auction.getZipName(element))
    }

    @Test
    fun `zip service should not repeat`() {
        assertEquals("Brucker Auction Service", Auction.getZipService(zipListing()))
    }

    @Test
    fun `display name should append location for brucker`() {
        assertEquals(
            "Auction - 123 Main St, Jackson, MO",
            Auction.getDisplayName("Auction", "Brucker Auction Service", "123 Main St, Jackson, MO")
        )
    }

    @Test
    fun `display name should leave other services alone`() {
        assertEquals("Estate Sale", Auction.getDisplayName("Estate Sale", "Other Auctions", "123 Main St"))
    }

    @Test
    fun `display name should not append blank or duplicate location`() {
        assertEquals("Auction", Auction.getDisplayName("Auction", "Brucker Auction Service", ""))
        assertEquals(
            "Auction at 123 Main St",
            Auction.getDisplayName("Auction at 123 Main St", "Brucker Auction Service", "123 Main St")
        )
    }

    @Test
    fun `display name should use location when brucker name is blank`() {
        assertEquals("123 Main St", Auction.getDisplayName("", "Brucker Auction Service", "123 Main St"))
    }

    @Test
    fun `zip name and service should be empty when missing`() {
        val element = Jsoup.parse("<div></div>").body().child(0)
        assertEquals("", Auction.getZipName(element))
        assertEquals("", Auction.getZipService(element))
    }
}
