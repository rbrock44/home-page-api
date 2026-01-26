package com.projects.homepageapi.controllers

import com.projects.homepageapi.*
import com.projects.homepageapi.models.SpotPrices
import com.projects.homepageapi.services.SpotPriceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("spot-price")
@CrossOrigin(
    origins = [
        angularOrigin,
        wildcardOrigin,
        cleaningScheduleOrigin,
        flashCardsOrigin,
        homePageOrigin,
        utilitiesOrigin,
    ],
    maxAge = maxAge
)
class SpotPriceController(
    @Autowired private val service: SpotPriceService
) {
    @GetMapping("")
    fun getSpotPrices(): SpotPrices {
        service.getSpotPrices();
    }
}
