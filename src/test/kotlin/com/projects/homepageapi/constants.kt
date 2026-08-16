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
        val mma2Document: Document = Jsoup.parse(File("src/test/resources/mma2.html").readText())

        val gdqDocument: Document = Jsoup.parse(File("src/test/resources/gdq.html").readText())

        val nbaApiJson: String = File("src/test/resources/espn-nba-scoreboard.json").readText()
        val nflApiJson: String = File("src/test/resources/espn-nfl-scoreboard.json").readText()

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
        val mma2Expected: FightCard = FightCard(
            listOf(
                Fight(
                    "Merab Dvalishvili vs Petr Yan",
                    "https://www.mmafighting.com/fight/453879/merab-dvalishvili-vs-petr-yan",
                    true
                ),
                Fight(
                    "Alexandre Pantoja vs Joshua Van",
                    "https://www.mmafighting.com/fight/453881/alexandre-pantoja-vs-joshua-van",
                    true
                ),
                Fight(
                    "Henry Cejudo vs Payton Talbott",
                    "https://www.mmafighting.com/fight/453883/henry-cejudo-vs-payton-talbott",
                    false
                ),
                Fight(
                    "Brandon Moreno vs Tatsuro Taira",
                    "https://www.mmafighting.com/fight/453885/brandon-moreno-vs-tatsuro-taira",
                    false
                ),
                Fight(
                    "Jan Blachowicz vs Bogdan Guskov",
                    "https://www.mmafighting.com/fight/453887/jan-blachowicz-vs-bogdan-guskov",
                    false
                )
            ),
            listOf(
                Fight(
                    "Grant Dawson vs Manuel Torres",
                    "https://www.mmafighting.com/fight/457069/grant-dawson-vs-manuel-torres",
                    false
                ),
                Fight(
                    "Terrance McKinney vs Chris Duncan",
                    "https://www.mmafighting.com/fight/457075/david-mckinney-vs-chris-duncan",
                    false
                ),
                Fight(
                    "Maycee Barber vs Karine Silva",
                    "https://www.mmafighting.com/fight/457061/maycee-barber-vs-karine-silva",
                    false
                ),
                Fight(
                    "Nazim Sadykhov vs Fares Ziam",
                    "https://www.mmafighting.com/fight/457063/nazim-sadykhov-vs-fares-ziam",
                    false
                ),
                Fight(
                    "Marvin Vettori vs Brunno Ferreira",
                    "https://www.mmafighting.com/fight/457057/marvin-vettori-vs-brunno-ferreira",
                    false
                ),
                Fight(
                    "Edson Barboza vs Jalin Turner",
                    "https://www.mmafighting.com/fight/457065/edson-barboza-vs-jalin-turner",
                    false
                ),
                Fight(
                    "Iwo Baraniewski vs Ibo Aslan",
                    "https://www.mmafighting.com/fight/457067/iwo-baraniewski-vs-ibo-aslan",
                    false
                ),
                Fight(
                    "Mansur Abdul-Malik vs Antonio Trocoli",
                    "https://www.mmafighting.com/fight/457073/mansur-abdul-malik-vs-antonio-trocoli",
                    false
                ),
                Fight(
                    "Muhammad Naimov vs Mairon Santos",
                    "https://www.mmafighting.com/fight/457071/muhammad-naimov-vs-mairon-santos",
                    false
                )
            ),
            "December 7, 2025",
            "UFC 323: Dvalishvili vs. Yan 2",
            "https://www.mmafighting.com/fight-card/453877/ufc-323-dvalishvili-vs-yan-2"
        )

        val nbaApiExpected: GamesPerDate = GamesPerDate(
            listOf(
                Game(
                    opponent = "Miami Heat",
                    opponentImageLink = "https://a.espncdn.com/i/teamlogos/nba/500/scoreboard/mia.png",
                    opponentTeamLink = "https://www.espn.com/nba/team/_/name/mia/miami-heat",
                    opponentRecord = "",
                    home = "Toronto Raptors",
                    homeImageLink = "https://a.espncdn.com/i/teamlogos/nba/500/scoreboard/tor.png",
                    homeTeamLink = "https://www.espn.com/nba/team/_/name/tor/toronto-raptors",
                    homeRecord = "",
                    time = "2026-10-03T23:00Z"
                )
            ),
            "Saturday, October 3, 2026 - Preseason"
        )

        val nflApiExpected: GamesPerDate = GamesPerDate(
            listOf(
                Game(
                    opponent = "Dallas Cowboys",
                    opponentImageLink = "https://a.espncdn.com/i/teamlogos/nfl/500/scoreboard/dal.png",
                    opponentTeamLink = "https://www.espn.com/nfl/team/_/name/dal/dallas-cowboys",
                    opponentRecord = "",
                    home = "Seattle Seahawks",
                    homeImageLink = "https://a.espncdn.com/i/teamlogos/nfl/500/scoreboard/sea.png",
                    homeTeamLink = "https://www.espn.com/nfl/team/_/name/sea/seattle-seahawks",
                    homeRecord = "",
                    time = "11:00 - 4th"
                ),
                Game(
                    opponent = "Carolina Panthers",
                    opponentImageLink = "https://a.espncdn.com/i/teamlogos/nfl/500/scoreboard/car.png",
                    opponentTeamLink = "https://www.espn.com/nfl/team/_/name/car/carolina-panthers",
                    opponentRecord = "",
                    home = "Buffalo Bills",
                    homeImageLink = "https://a.espncdn.com/i/teamlogos/nfl/500/scoreboard/buf.png",
                    homeTeamLink = "https://www.espn.com/nfl/team/_/name/buf/buffalo-bills",
                    homeRecord = "",
                    time = "CAR 14, BUF 29"
                ),
                Game(
                    opponent = "Cleveland Browns",
                    opponentImageLink = "https://a.espncdn.com/i/teamlogos/nfl/500/scoreboard/cle.png",
                    opponentTeamLink = "https://www.espn.com/nfl/team/_/name/cle/cleveland-browns",
                    opponentRecord = "",
                    home = "Chicago Bears",
                    homeImageLink = "https://a.espncdn.com/i/teamlogos/nfl/500/scoreboard/chi.png",
                    homeTeamLink = "https://www.espn.com/nfl/team/_/name/chi/chicago-bears",
                    homeRecord = "",
                    time = "CLE 10, CHI 34"
                ),
                Game(
                    opponent = "Minnesota Vikings",
                    opponentImageLink = "https://a.espncdn.com/i/teamlogos/nfl/500/scoreboard/min.png",
                    opponentTeamLink = "https://www.espn.com/nfl/team/_/name/min/minnesota-vikings",
                    opponentRecord = "",
                    home = "New York Giants",
                    homeImageLink = "https://a.espncdn.com/i/teamlogos/nfl/500/scoreboard/nyg.png",
                    homeTeamLink = "https://www.espn.com/nfl/team/_/name/nyg/new-york-giants",
                    homeRecord = "",
                    time = "MIN 13, NYG 10"
                ),
                Game(
                    opponent = "Los Angeles Rams",
                    opponentImageLink = "https://a.espncdn.com/i/teamlogos/nfl/500/scoreboard/lar.png",
                    opponentTeamLink = "https://www.espn.com/nfl/team/_/name/lar/los-angeles-rams",
                    opponentRecord = "",
                    home = "Kansas City Chiefs",
                    homeImageLink = "https://a.espncdn.com/i/teamlogos/nfl/500/scoreboard/kc.png",
                    homeTeamLink = "https://www.espn.com/nfl/team/_/name/kc/kansas-city-chiefs",
                    homeRecord = "",
                    time = "LAR 20, KC 12"
                ),
                Game(
                    opponent = "Jacksonville Jaguars",
                    opponentImageLink = "https://a.espncdn.com/i/teamlogos/nfl/500/scoreboard/jax.png",
                    opponentTeamLink = "https://www.espn.com/nfl/team/_/name/jax/jacksonville-jaguars",
                    opponentRecord = "",
                    home = "New Orleans Saints",
                    homeImageLink = "https://a.espncdn.com/i/teamlogos/nfl/500/scoreboard/no.png",
                    homeTeamLink = "https://www.espn.com/nfl/team/_/name/no/new-orleans-saints",
                    homeRecord = "",
                    time = "JAX 24, NO 20"
                ),
                Game(
                    opponent = "Philadelphia Eagles",
                    opponentImageLink = "https://a.espncdn.com/i/teamlogos/nfl/500/scoreboard/phi.png",
                    opponentTeamLink = "https://www.espn.com/nfl/team/_/name/phi/philadelphia-eagles",
                    opponentRecord = "",
                    home = "Baltimore Ravens",
                    homeImageLink = "https://a.espncdn.com/i/teamlogos/nfl/500/scoreboard/bal.png",
                    homeTeamLink = "https://www.espn.com/nfl/team/_/name/bal/baltimore-ravens",
                    homeRecord = "",
                    time = "PHI 7, BAL 24"
                )
            ),
            "Saturday, August 15, 2026 - Preseason"
        )
    }
}
