package com.projects.homepageapi.models

import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

data class Auction(
    val service: String,
    val name: String,
    val internetBidding: Boolean,
    val url: String,
    val startDate: String,
    val endDate: String,
    val location: String,
    val note: String,
) {
    companion object {
        @JvmStatic
        fun getHibidAuctions(doc: Document): Elements {
            return  doc.getElementsByClass("auction-header")
        }

        @JvmStatic
        fun getZipAuctions(doc: Document): Elements {
            return  doc.getElementsByClass("az-ListOfLlisting__Container")
        }

        @JvmStatic
        fun getHibidName(element: Element): String {
            val name = element.getElementsByClass("auction-title")
            return if (name.isNullOrEmpty()) "" else name.text()
        }

        @JvmStatic
        fun getZipName(element: Element): String {
            val name = element.getElementsByClass("az_title_href")
            return if (name.isNullOrEmpty()) "" else name.text()
        }

        @JvmStatic
        fun getHibidUrl(element: Element): String {
            val title = element.getElementsByClass("auction-header-title")[0]
            val url = title.getElementsByTag("a")
            return if (url.isNullOrEmpty()) "" else "https://www.hibid.com${url.attr("href")}"
        }

        @JvmStatic
        fun getZipUrl(element: Element): String {
            val url = element.getElementsByClass("az_title_href")
            return if (url.isNullOrEmpty()) "" else url.attr("href")
        }

        @JvmStatic
        fun getHibidService(element: Element): String {
            val service = element.getElementsByClass("lot-company-page-link")
            return if (service.isNullOrEmpty()) "" else service.text()
        }

        @JvmStatic
        fun getZipService(element: Element): String {
            val service = element.getElementsByClass("az-ListOfLlisting__company--title")
            return if (service.isNullOrEmpty()) "" else service.text()
        }

        @JvmStatic
        fun getHibidStartDate(element: Element): String {
            val date = getHibidPlainText(element, 0)
            return getDateByIndex(date, 0)
        }

        @JvmStatic
        fun getZipStartDate(element: Element): String {
            return getZipEventDetail(element = element, index = 0)
        }

        @JvmStatic
        fun getHibidEndDate(element: Element): String {
            val date = getHibidPlainText(element, 0)
            return getDateByIndex(date, 1)
        }

        @JvmStatic
        fun getHibidLocation(element: Element): String {
            val col = element.getElementsByClass("col")[0]
            val hovertext = col.getElementsByClass("hovertext")[0]

            val address = hovertext.getElementsByTag("strong")[0]
            return if (address == null) "" else address.text()
        }

        @JvmStatic
        fun getZipLocation(element: Element): String {
            return getZipEventDetail(element = element, index = 1)

        }

        @JvmStatic
        fun getHibidInternetBidding(element: Element): Boolean {
            val name = element.getElementsByClass("auction-type")
            return !(if (name.isNullOrEmpty()) "" else name.text()).contains(
                "No internet bidding",
                ignoreCase = true
            )
        }

        @JvmStatic
        fun getHibidNote(element: Element): String {
            // second p element
            return getHibidPlainText(element, 1)
        }

        private fun getHibidPlainText(element: Element, index: Int): String {
            val col = element.getElementsByClass("col")[0]

            val p = col.getElementsByTag("p")[index]
            return if (p == null) "" else p.text()
        }

        private fun getDateByIndex(input: String, index: Int): String {
            val datePart = input.removePrefix("Date(s)").trim()
            val dates = datePart.split(" - ")

            return dates.getOrNull(index) ?: "Invalid index"
        }
        private fun getZipEventDetail(element: Element, index: Int): String {
            val input = element.getElementsByClass("az-ListOfLlisting__date")[0].text()
            val splitParts = input.split(" - ", limit = 3)
            return when (index) {
                0 -> splitParts.getOrNull(0)?.trim() ?: ""
                1 -> splitParts.getOrNull(2)?.trim() ?: ""
                else -> throw IllegalArgumentException("Index must be 0 or 1")
            }
        }

    }
}
