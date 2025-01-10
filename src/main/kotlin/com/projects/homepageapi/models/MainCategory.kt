package com.projects.homepageapi.models

import kotlinx.serialization.Serializable

@Serializable
data class MainCategory(
    val id: String,
    val name: String,
    val subCategories: List<SubCategory>
) {
}
