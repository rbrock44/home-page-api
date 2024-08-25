package com.projects.homepageapi.models

data class Meeting(
    val id: Int = 0,
    val date: String = "",
    val startTime: String = "",
    val endTime: String = "",
    val title: String = "",
    val person: String = "",
) {
    constructor(line: String) : this(){
        //TODO: pick delimiter
        val items = line.split("")
        Meeting(items[0].toInt(), items[1], items[2], items[3], items[4], items[5])
    }
}
