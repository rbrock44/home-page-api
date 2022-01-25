package com.projects.homepageapi.models

data class GamesPerDate(
    val games: List<Game>,
    val date: String
) {
}
