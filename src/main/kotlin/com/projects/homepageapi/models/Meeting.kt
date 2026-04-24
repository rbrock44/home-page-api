package com.projects.homepageapi.models

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

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
    companion object {
        fun fromLine(line: String): Meeting {
            val items = line.split("|")
            return Meeting(items[0].toInt(), items[1], items[2], items[3], items[4], items[5])
        }
    }
}
