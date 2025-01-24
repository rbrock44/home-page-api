package com.projects.homepageapi.controllers

import com.projects.homepageapi.angularOrigin
import com.projects.homepageapi.domainOrigin
import com.projects.homepageapi.maxAge
import com.projects.homepageapi.models.Event
import com.projects.homepageapi.services.ScrapingHelperService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("gdq")
@CrossOrigin(origins = [angularOrigin, domainOrigin], maxAge = maxAge)
class GdqController(
    @Autowired private val service: ScrapingHelperService
) {
    @GetMapping("/upcoming")
    fun getUpcoming(): Event {
        return service.parseGdqWebsite()
    }
}
