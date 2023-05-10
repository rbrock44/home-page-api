package com.projects.homepageapi.controllers

import com.projects.homepageapi.angularOrigin
import com.projects.homepageapi.maxAge
import com.projects.homepageapi.services.HomeMediaService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("home-media-search")
@CrossOrigin(origins = [angularOrigin], maxAge = maxAge)
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
