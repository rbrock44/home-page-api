package com.projects.homepageapi.models

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Meeting(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,
    val date: String = "",
    val startTime: String = "",
    val endTime: String = "",
    val title: String = "",
    val person: String = ""
) {
    constructor(line: String) : this() {
        val items = line.split("|")
        Meeting(items[0].toInt(), items[1], items[2], items[3], items[4], items[5])
    }
}
