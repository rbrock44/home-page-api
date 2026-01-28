package com.projects.homepageapi.services

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.springframework.stereotype.Service

@Service
class JsoupService() {
    fun connect(url: String): Document {
        return if (url.contains("auctionzip")) {
            Jsoup.connect(url)
                .header("Host", "www.auctionzip.com")
                .userAgent("PostmanRuntime/7.43.0")
                .cookie("__cf_bm", "dVM0yLW9pdhVFJLIa7GMhZV9JtylgKQkScoPqch155E-1737657761-1.0.1.1-yxerctr49NF0M7SQSp2eCZJJd6NULdYuLQScx25sZH.90kh16hFs81zdnzTH8pgl5gGxw8FKcK5JUeW8exgFHA")
                .cookie("az_view", "1")
                .cookie("h2cload", "A--D-------")
                .get()
        } else {
            Jsoup.connect(url).get()
        }
    }
}
