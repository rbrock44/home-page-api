package com.projects.homepageapi.models

import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.time.Instant


data class SpotPrices(
    val gold: Double,
    val silver: Double,
    val timeStamp: String = Instant.now().toString()
) {
    companion object {
        @JvmStatic
        fun getElement(doc: Document): String? {
            return doc.select("div[data-test='instrument-price-last']").firstOrNull()?.text();
        }
    }
}
