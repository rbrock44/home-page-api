package com.projects.homepageapi.services

import com.projects.homepageapi.models.SpotPrices
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SpotPriceService(
    @Autowired private val helper: ScrapingHelperService
) {
    fun getSpotPrices(): SpotPrices {
        return {
            gold = helper.parseGoldWebsite(),
            silver = helper.parseSilverWebsite()
        }
    }
}