package com.projects.homepageapi.models

data class Event(
    val name: List<String> = emptyList(),
    val dates: List<String> = emptyList(),
    val url: String = ""
) {
    constructor(name: String, dates: String, url: String) : this(listOf(name), listOf(dates), url)
}
