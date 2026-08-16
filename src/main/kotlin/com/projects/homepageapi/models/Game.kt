package com.projects.homepageapi.models

data class Game(
    val opponent: String,
    val opponentImageLink: String,
    val opponentTeamLink: String,
    val opponentRecord: String,
    val home: String,
    val homeImageLink: String,
    val homeTeamLink: String,
    val homeRecord: String,
    val time: String
)
