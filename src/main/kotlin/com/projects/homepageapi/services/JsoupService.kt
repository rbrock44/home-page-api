package com.projects.homepageapi.services

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.springframework.stereotype.Service

@Service
class JsoupService() {
    fun connect(url: String): Document {
        return if (url.contains("auctionzip")) {
            Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0 Safari/537.36")
                .cookie("__cf_bm", "dVM0yLW9pdhVFJLIa7GMhZV9JtylgKQkScoPqch155E-1737657761-1.0.1.1-yxerctr49NF0M7SQSp2eCZJJd6NULdYuLQScx25sZH.90kh16hFs81zdnzTH8pgl5gGxw8FKcK5JUeW8exgFHA")
                .cookie("az_view", "1")
                .cookie("h2cload", "A--D-------")
                .get()
        } else {
            Jsoup.connect(url).get()
        }
    }

    fun getJson(url: String): String {
        return Jsoup.connect(url).ignoreContentType(true).execute().body()
    }
}
