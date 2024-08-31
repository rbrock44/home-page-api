package com.projects.homepageapi.controllers

import com.projects.homepageapi.angularOrigin
import com.projects.homepageapi.maxAge
import com.projects.homepageapi.services.HomeMediaService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("media")
@CrossOrigin(origins = [angularOrigin], maxAge = maxAge)
class MediaController(
    @Autowired private val mediaService: HomeMediaService
) {
    @GetMapping("/update")
    fun updateMedia() {
        mediaService.getMediaFilesFromRepo();
    }
}
