package com.projects.homepageapi.models

import kotlinx.serialization.Serializable

@Serializable
data class FlashCardData(
    val categories: List<MainCategory>,
) {
}
