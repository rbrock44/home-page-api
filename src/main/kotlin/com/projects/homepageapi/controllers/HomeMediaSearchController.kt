package com.projects.homepageapi.controllers

import com.projects.homepageapi.*
import com.projects.homepageapi.services.HomeMediaService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("home-media-search")
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
class HomeMediaSearchController(
    @Autowired private val service: HomeMediaService
) {
    @GetMapping()
    fun getFilenames(
        @RequestParam criteria: String = ""
    ): List<String> {
        return service.getFilenamesThatContain(criteria)
    }
}
