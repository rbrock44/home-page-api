package com.projects.homepageapi.models

import kotlinx.serialization.Serializable

@Serializable
data class FlashCard(
    val id: String,
    val question: String,
    val answer: String,
    val example: String? = "",
    val type: String? = ""
) {
}
