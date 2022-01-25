package com.projects.homepageapi.models

import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

data class FightCard(
    val main: List<Fight>,
    val under: List<Fight>,
    val date: String,
    val title: String,
    val titleLink: String,
) {
    companion object {
        @JvmStatic
        fun getCard(elements: Element, index: Int): Element {
            return elements.getElementsByClass("m-mmaf-pte-event-list__split-item")[index]
        }

        @JvmStatic
        fun getCards(doc: Document): Elements {
            return doc.getElementsByClass("m-mmaf-pte-event-list__split")
        }

        @JvmStatic
        fun getDates(doc: Document): Elements {
            return doc.getElementsByTag("h3")
        }

        @JvmStatic
        fun getTitles(doc: Document): Elements {
            return doc.getElementsByTag("h2")
        }

    }
}
