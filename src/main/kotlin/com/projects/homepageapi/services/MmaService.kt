package com.projects.homepageapi.services

import com.projects.homepageapi.models.Fight
import com.projects.homepageapi.models.FightCard
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class MmaService(
    @Autowired private val helper: ScrapingHelperService
) {
    fun getFightsToday(): FightCard {
        return parseWebsite(formattedDate = helper.getCurrentDate())
    }

    fun getUpcomingCard(): FightCard {
        return parseWebsite()
    }

    fun addFightsToList(list: MutableList<Fight>, card: Elements) {
        for (fight in card) {
            val fightTitle: String = Fight.getTitle(fight)
            val fightLink: String = Fight.getLink(fight)
            val isTitle: Boolean = Fight.isTitleFight(fight)

            list.add(
                Fight(
                    title = fightTitle,
                    link = fightLink,
                    isTitleFight = isTitle
                )
            )
        }
    }

    private fun parseWebsite(formattedDate: String = ""): FightCard {
        val doc: Document = Jsoup.connect("https://www.mmafighting.com/schedule").get()
        val fightCard: Elements = FightCard.getCards(doc)
        val cardDates: Elements = FightCard.getDates(doc)
        val cardTitles: Elements = FightCard.getTitles(doc)

        val listOfMainFights: MutableList<Fight> = mutableListOf()
        val listOfUnderFights: MutableList<Fight> = mutableListOf()
        var title = ""
        var titleLink = ""

        val pair = helper.getIndexAndDate(
            formattedDate = formattedDate,
            tableDates = cardDates,
            isMmaFormat = true
        )

        val index = pair.first
        val date = if (index == 0) cardDates[index].text() else pair.second

        if (index != -1) {
            title = cardTitles[index].text()
            titleLink = cardTitles[index].getElementsByTag("a").attr("href")

            val card = fightCard[index]
            val mainCard = FightCard.getCard(card, 0).getElementsByTag("li")
            val underCard = FightCard.getCard(card, 1).getElementsByTag("li")

            addFightsToList(listOfMainFights, mainCard)
            addFightsToList(listOfUnderFights, underCard)
        }

        return FightCard(
            main = listOfMainFights,
            under = listOfUnderFights,
            date = date,
            title = title,
            titleLink = titleLink
        )
    }
}


