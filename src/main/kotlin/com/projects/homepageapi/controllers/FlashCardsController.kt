package com.projects.homepageapi.controllers

import com.projects.homepageapi.angularOrigin
import com.projects.homepageapi.maxAge
import com.projects.homepageapi.models.FlashCardData
import com.projects.homepageapi.services.FlashCardsService
import com.projects.homepageapi.services.HomeMediaService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("flash-cards")
@CrossOrigin(origins = [angularOrigin], maxAge = maxAge)
class FlashCardsController(
    @Autowired private val flashCardsService: FlashCardsService
) {
    @GetMapping("")
    fun get(): FlashCardData {
        return flashCardsService.getFlashCardFileFromRepo()
    }
}
