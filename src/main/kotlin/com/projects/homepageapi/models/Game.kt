package com.projects.homepageapi.models

import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

data class Game(
    val opponent: String,
    val opponentImageLink: String,
    val opponentTeamLink: String,
    val opponentRecord: String,
    val home: String,
    val homeImageLink: String,
    val homeTeamLink: String,
    val homeRecord: String,
    val time: String
) {
    companion object {
        @JvmStatic
        fun getImage(elements: Element, index: Int): String {
            return elements.getElementsByTag("img")[index].attr("src")
        }

        @JvmStatic
        fun getFootballImageLink(elements: Element, index: Int): String {
            return elements.getElementsByTag("img")[index].attr("src")
        }

        @JvmStatic
        fun getTeamLink(elements: Element, index: Int): String {
            return "https://www.espn.com" + elements.getElementsByTag("a")[index].attr("href")
        }

        @JvmStatic
        fun getFootballData(doc: Document): Elements {
            return doc.getElementsByTag("table")
        }

        @JvmStatic
        fun getDates(doc: Document): Elements {
            return doc.getElementsByClass("table-caption")
        }

        @JvmStatic
        fun getFootballName(elements: Element, index: Int): String {
            return elements.getElementsByTag("abbr")[index].attr("title")
        }

        @JvmStatic
        fun getFootballTime(elements: Element): String {
            val values = elements.getElementsByAttribute("data-behavior");
            return if (values.size > 2) {
                values[2].attr("data-date")
            } else {
                ""
            }
        }

        @JvmStatic
        fun getBasketballName(elements: Element, index: Int): String {
            return elements.getElementsByTag("abbr")[index].attr("title")
        }

        @JvmStatic
        fun getBasketballTime(elements: Element): String {
            return elements.getElementsByClass("imso_mh__lr-dt-ds").text()
        }

        @JvmStatic
        fun getBasketballData(doc: Document): Elements {
            return doc.getElementsByClass("responsive-table-wrap")
        }
    }
}
