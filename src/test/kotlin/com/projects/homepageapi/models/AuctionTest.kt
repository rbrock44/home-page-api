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
    fun `zip name and service should be empty when missing`() {
        val element = Jsoup.parse("<div></div>").body().child(0)
        assertEquals("", Auction.getZipName(element))
        assertEquals("", Auction.getZipService(element))
    }
}
