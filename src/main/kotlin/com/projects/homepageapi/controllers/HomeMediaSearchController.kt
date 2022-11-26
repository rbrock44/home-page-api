package com.projects.homepageapi.controllers

import com.projects.homepageapi.services.HomeMediaService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("home-media-search")
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
