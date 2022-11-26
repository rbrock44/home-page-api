package com.projects.homepageapi

import org.jsoup.nodes.Document
import java.io.File

internal class Constants {
    companion object {
        val mmaDocument = Document("https://www.mmafighting.com/schedule").prepend(File("src/test/resources/mma.html").readText())

        val nbaDocument = Document("https://www.espn.com/nba/schedule").prepend(File("src/test/resources/espn-nba.html").readText())

        val nflDocument = Document("https://www.espn.com/nfl/schedule").prepend(File("src/test/resources/espn-nfl.html").readText())
    }
}
