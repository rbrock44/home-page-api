package com.projects.homepageapi.controllers

import com.projects.homepageapi.angularOrigin
import com.projects.homepageapi.maxAge
import com.projects.homepageapi.models.Auction
import com.projects.homepageapi.services.AuctionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("auction")
@CrossOrigin(origins = [angularOrigin], maxAge = maxAge)
class AuctionController(
    @Autowired private val auctionService: AuctionService
) {
    @GetMapping("/today")
    fun getAuctionsToday(): List<Auction>{
        return auctionService.getAuctionsToday()
    }

    @GetMapping("/upcoming")
    fun getAuctionsUpcoming(): List<Auction> {
        return auctionService.getUpcomingAuctions()
    }
}
