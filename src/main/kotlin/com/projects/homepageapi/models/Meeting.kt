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
    val person: String = "",
    val hasBeenPaid: Boolean = false
) {
    companion object {
        fun fromLine(line: String): Meeting {
            val items = line.split("|")
            return Meeting(
                id = items[0].toInt(),
                date = items[1],
                startTime = items[2],
                endTime = items[3],
                title = items[4],
                person = items[5],
                hasBeenPaid = false
            )
        }
    }
}
