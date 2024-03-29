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
            return elements.getElementsByClass("team__logo")[index].attr("src")
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
            return  doc.getElementsByClass("table")
        }

        @JvmStatic
        fun getDates(doc: Document): Elements {
            return doc.getElementsByClass("Table__Title")
        }

        @JvmStatic
        fun getFootballDates(doc: Document): Elements {
            return doc.getElementsByClass("Table__Title")
        }

        @JvmStatic
        fun getFootballName(elements: Element, index: Int): String {
            val teams = elements.getElementsByClass("table__team")
            val value = teams[index]
            return if (value != null) {
                val a = value.getElementsByTag("a")
                if (a.size >= 2) {
                    a[1].text()
                } else {
                    ""
                }
            } else {
                ""
            }
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
            val teams = elements.getElementsByClass("Table__Team")
            val value = teams[index]
            return if (value != null) {
                val children = value.children()
                if (children.size >= 1) {
                    if (children.size == 1) {
                        children[0].text()
                    }
                    children[1].text()
                } else {
                    ""
                }
            } else {
                ""
            }
        }

        @JvmStatic
        fun getBasketballTime(elements: Element): String {
            return elements.getElementsByClass("date__col").text()
        }

        @JvmStatic
        fun getBasketballData(doc: Document): Elements {
            return doc.getElementsByClass("ResponsiveTable")
        }
    }
}
