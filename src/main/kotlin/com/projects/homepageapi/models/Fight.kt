package com.projects.homepageapi.models

import org.jsoup.nodes.Element

data class Fight(
    val title: String,
    val link: String,
    val isTitleFight: Boolean,
) {
    companion object {
        @JvmStatic
        fun getLink(elements: Element): String {
            return elements.getElementsByTag("a").attr("href")
        }

        @JvmStatic
        fun getTitle(elements: Element): String {
            return elements.getElementsByTag("a").text()
        }

        @JvmStatic
        fun isTitleFight(elements: Element): Boolean {
            return elements.getElementsByClass("m-mmaf-pte__title-fight").size > 0
        }
    }
}
