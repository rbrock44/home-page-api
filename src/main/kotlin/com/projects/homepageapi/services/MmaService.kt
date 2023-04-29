package com.projects.homepageapi.services

import com.projects.homepageapi.models.FightCard
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MmaService(
    @Autowired private val helper: ScrapingHelperService
) {
    fun getFightsToday(): FightCard {
        return helper.parseMmaWebsite(formattedDate = helper.getCurrentDate())
    }

    fun getUpcomingCard(): FightCard {
        return helper.parseMmaWebsite()
    }
}
