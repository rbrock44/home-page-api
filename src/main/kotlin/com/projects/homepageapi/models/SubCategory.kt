package com.projects.homepageapi.models

import kotlinx.serialization.Serializable

@Serializable
data class SubCategory(
    val id: String,
    val name: String,
    val flashCards: List<FlashCard>
) {
}
