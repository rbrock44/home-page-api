package com.projects.homepageapi.services

import com.projects.homepageapi.models.Auction
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AuctionService(
    @Autowired private val helper: ScrapingHelperService
) {
    fun getAuctionsToday(): List<Auction> {
        return parseWebsite(formattedDate = helper.getCurrentDate())
    }

    fun getUpcomingAuctions(): List<Auction> {
        return parseWebsite()
    }

    private fun parseWebsite(formattedDate: String = ""): List<Auction> {
        return helper.parseAuctionWebsites(
            formattedDate = formattedDate
        )
    }
}
