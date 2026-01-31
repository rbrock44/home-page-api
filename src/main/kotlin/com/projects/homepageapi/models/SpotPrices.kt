package com.projects.homepageapi.models

import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.time.Instant
import com.projects.homepageapi.*

data class SpotPrices(
    val gold: Double,
    val silver: Double,
    val description: String = "",
    val silverSource: String = silverSpotOrigin,
    val goldSource: String = goldSpotOrigin,
    val timeStamp: String = Instant.now().toString()
) {
    companion object {
        @JvmStatic
        fun getElement(doc: Document): String? {
           return doc.selectFirst("h3")?.text()?.trim()
        }
    }
}
