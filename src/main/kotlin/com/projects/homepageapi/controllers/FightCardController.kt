package com.projects.homepageapi.controllers

import com.projects.homepageapi.angularOrigin
import com.projects.homepageapi.domainOrigin
import com.projects.homepageapi.maxAge
import com.projects.homepageapi.models.FightCard
import com.projects.homepageapi.services.MmaService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("fight-card")
@CrossOrigin(origins = [angularOrigin, domainOrigin], maxAge = maxAge)
class FightCardController(
    @Autowired private val mmaService: MmaService
) {
    @GetMapping("/today")
    fun getFightsToday(): FightCard {
        return mmaService.getFightsToday()
    }

    @GetMapping("/upcoming")
    fun getUpcomingCard(): FightCard {
        return mmaService.getUpcomingCard()
    }
}
