package com.projects.homepageapi

import com.projects.homepageapi.models.Fight
import com.projects.homepageapi.models.FightCard
import com.projects.homepageapi.models.Game
import com.projects.homepageapi.models.GamesPerDate
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.File

internal class Constants {
    companion object {
        val mmaDocument: Document = Jsoup.parse(File("src/test/resources/mma.html").readText())

        val nbaDocument: Document = Jsoup.parse(File("src/test/resources/espn-nba.html").readText())

        val nflDocument: Document = Jsoup.parse(File("src/test/resources/espn-nfl.html").readText())

        val gdqDocument: Document = Jsoup.parse(File("src/test/resources/gdq.html").readText())

        val nflExpected: GamesPerDate = GamesPerDate(
            listOf(
                Game(
                    opponent = "Buffalo",
                    opponentImageLink = "data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7",
                    opponentTeamLink = "https://www.espn.com/nfl/team/_/name/buf/buffalo-bills",
                    opponentRecord = "",
                    home = "Detroit",
                    homeImageLink = "data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7",
                    homeTeamLink = "https://www.espn.com/nfl/team/_/name/det/detroit-lions",
                    homeRecord = "",
                    time = "11:30 AM"
                ),
                Game(
                    opponent = "New York",
                    opponentImageLink = "data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7",
                    opponentTeamLink = "https://www.espn.com/nfl/team/_/name/nyg/new-york-giants",
                    opponentRecord = "",
                    home = "Dallas",
                    homeImageLink = "data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7",
                    homeTeamLink = "https://www.espn.com/nfl/team/_/name/dal/dallas-cowboys",
                    homeRecord = "",
                    time = "3:30 PM"
                ),
                Game(
                    opponent = "New England",
                    opponentImageLink = "data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7",
                    opponentTeamLink = "https://www.espn.com/nfl/team/_/name/ne/new-england-patriots",
                    opponentRecord = "",
                    home = "Minnesota",
                    homeImageLink = "data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7",
                    homeTeamLink = "https://www.espn.com/nfl/team/_/name/min/minnesota-vikings",
                    homeRecord = "",
                    time = "7:20 PM"
                )
            ),
            "Thursday, November 24, 2022"
        )

        val mmaExpected: FightCard = FightCard(
            listOf(
                Fight(
                    "Kayla Harrison vs. Larissa Pacheco",
                    "https://www.mmafighting.com/fight/13181/kayla-harrison-vs-larissa-pacheco",
                    true
                ),
                Fight(
                    "Brendan Loughnane vs. Bubba Jenkins",
                    "https://www.mmafighting.com/fight/13182/brandon-loughnane-vs-bubba-jenkins",
                    true
                ),
                Fight(
                    "Ante Delija vs. Matheus Scheffel",
                    "https://www.mmafighting.com/fight/13184/ante-delija-vs-matheus-scheffel",
                    true
                ),
                Fight(
                    "Aspen Ladd vs. Julia Budd",
                    "https://www.mmafighting.com/fight/13185/aspen-ladd-vs-julia-budd",
                    false
                ),
                Fight(
                    "Olivier Aubin-Mercier vs. Stevie Ray",
                    "https://www.mmafighting.com/fight/13186/olivier-aubin-mercier-vs-stevie-ray",
                    true
                ),
                Fight(
                    "Sadibou Sy vs. Dilano Taylor",
                    "https://www.mmafighting.com/fight/13187/sadibou-sy-vs-dilano-taylor",
                    true
                ),
                Fight(
                    "Rob Wilkinson vs. Omari Akhmedov",
                    "https://www.mmafighting.com/fight/13188/rob-wilkinson-vs-omari-akhmedov",
                    true
                ),


                ),
            listOf(
                Fight(
                    "Sheymon Moraes vs. Marlon Moraes",
                    "https://www.mmafighting.com/fight/13183/sheymon-moraes-vs-marlon-moraes",
                    false
                ),
                Fight(
                    "Natan Schulte vs. Jeremy Stephens",
                    "https://www.mmafighting.com/fight/13189/natan-schulte-vs-jeremy-stephens",
                    false
                ),
                Fight(
                    "Magomed Magomedkerimov vs. Gleison Tibau",
                    "https://www.mmafighting.com/fight/13190/magomed-magomedkerimov-vs-gleison-tibau",
                    false
                ),
                Fight(
                    "Dakota Ditcheva vs. Katherine Corogenes",
                    "https://www.mmafighting.com/fight/13210/dakota-ditcheva-vs-katherine-corogenes",
                    false
                ),
                Fight(
                    "Biaggio Ali Walsh vs. Tom Graesser",
                    "https://www.mmafighting.com/fight/13211/biaggio-ali-walsh-vs-tom-graesser",
                    false
                )
            ),
            "November 25, 2022",
            "PFL World Championship 2022",
            "https://www.mmafighting.com/fight-card/1351/pfl-world-championship-2022"
        )

        val nbaExpected: GamesPerDate = GamesPerDate(
            listOf(
                Game(
                    "Philadelphia",
                    "data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7",
                    "https://www.espn.com/nba/team/_/name/phi/philadelphia-76ers",
                    "",
                    "Charlotte",
                    "data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7",
                    "https://www.espn.com/nba/team/_/name/cha/charlotte-hornets",
                    "",
                    "CHA 107, PHI 101"
                ),
                Game(
                    "Portland",
                    "data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7",
                    "https://www.espn.com/nba/team/_/name/por/portland-trail-blazers",
                    "",
                    "Cleveland",
                    "data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7",
                    "https://www.espn.com/nba/team/_/name/cle/cleveland-cavaliers",
                    "",
                    "CLE 114, POR 96"
                ),
                Game(
                    "Minnesota",
                    "data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7",
                    "https://www.espn.com/nba/team/_/name/min/minnesota-timberwolves",
                    "",
                    "Indiana",
                    "data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7",
                    "https://www.espn.com/nba/team/_/name/ind/indiana-pacers",
                    "",
                    "MIN 115, IND 101"
                ),
                Game(
                    "Sacramento",
                    "data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7",
                    "https://www.espn.com/nba/team/_/name/sac/sacramento-kings",
                    "",
                    "Atlanta",
                    "data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7",
                    "https://www.espn.com/nba/team/_/name/atl/atlanta-hawks",
                    "",
                    "ATL 115, SAC 106"
                ),
                Game(
                    "Dallas",
                    "data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7",
                    "https://www.espn.com/nba/team/_/name/dal/dallas-mavericks",
                    "",
                    "Boston",
                    "data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7",
                    "https://www.espn.com/nba/team/_/name/bos/boston-celtics",
                    "",
                    "BOS 125, DAL 112"
                ),
                Game(
                    "Washington",
                    "data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7",
                    "https://www.espn.com/nba/team/_/name/wsh/washington-wizards",
                    "",
                    "Miami",
                    "data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7",
                    "https://www.espn.com/nba/team/_/name/mia/miami-heat",
                    "",
                    "MIA 113, WSH 105"
                ),
                Game(
                    "Brooklyn",
                    "data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7",
                    "https://www.espn.com/nba/team/_/name/bkn/brooklyn-nets",
                    "",
                    "Toronto",
                    "data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7",
                    "https://www.espn.com/nba/team/_/name/tor/toronto-raptors",
                    "",
                    "BKN 112, TOR 98"
                ),
                Game(
                    "Chicago",
                    "data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7",
                    "https://www.espn.com/nba/team/_/name/chi/chicago-bulls",
                    "",
                    "Milwaukee",
                    "data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7",
                    "https://www.espn.com/nba/team/_/name/mil/milwaukee-bucks",
                    "",
                    "CHI 118, MIL 113"
                ),
                Game(
                    "Denver",
                    "data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7",
                    "https://www.espn.com/nba/team/_/name/den/denver-nuggets",
                    "",
                    "Oklahoma City",
                    "data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7",
                    "https://www.espn.com/nba/team/_/name/okc/oklahoma-city-thunder",
                    "",
                    "DEN 131, OKC 126 (OT)"
                ),
                Game(
                    "New Orleans",
                    "data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7",
                    "https://www.espn.com/nba/team/_/name/no/new-orleans-pelicans",
                    "",
                    "San Antonio",
                    "data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7",
                    "https://www.espn.com/nba/team/_/name/sa/san-antonio-spurs",
                    "",
                    "NO 129, SA 110"
                ),
                Game(
                    "Detroit",
                    "data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7",
                    "https://www.espn.com/nba/team/_/name/det/detroit-pistons",
                    "",
                    "Utah",
                    "data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7",
                    "https://www.espn.com/nba/team/_/name/utah/utah-jazz",
                    "",
                    "DET 125, UTAH 116"
                ),
                Game(
                    "LA",
                    "data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7",
                    "https://www.espn.com/nba/team/_/name/lac/la-clippers",
                    "",
                    "Golden State",
                    "data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///yH5BAEAAAAALAAAAAABAAEAAAIBRAA7",
                    "https://www.espn.com/nba/team/_/name/gs/golden-state-warriors",
                    "",
                    "GS 124, LAC 107"
                )
            ),
            "Wednesday, November 23, 2022",
        )
    }
}
