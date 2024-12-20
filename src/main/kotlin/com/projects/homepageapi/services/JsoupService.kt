package com.projects.homepageapi.services

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.springframework.stereotype.Service

@Service
class JsoupService() {
    fun connect(url: String): Document {
        return Jsoup.connect(url)
            .get()

    }
}
