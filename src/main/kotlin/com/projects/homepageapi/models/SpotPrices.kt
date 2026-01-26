package com.projects.homepageapi.models

import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

data class SpotPrices(
    val gold: String,
    val silver: String,
    val timeStamp: String = Instant.now().toString()
) {
    companion object {
        @JvmStatic
        fun getElement(doc: Document): String {
            return doc.select("div[data-test='instrument-price-last']").first().text();
        }
    }
}
