package com.projects.homepageapi.models

data class GamesPerDate(
    val games: List<Game>,
    val date: String,
    // yyyyMMdd (US Eastern) of the games, so callers can page to the next/previous game day
    val day: String = ""
) {
}
