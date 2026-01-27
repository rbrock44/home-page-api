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
        } else if (url.contains("investing.com")) {
            Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                .referrer("https://www.google.com")
                .timeout(10000)
                .get()
        } else {
            Jsoup.connect(url).get()
        }
    }
}
