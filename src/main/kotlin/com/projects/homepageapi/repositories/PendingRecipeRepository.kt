package com.projects.homepageapi.repositories

import com.projects.homepageapi.models.PendingRecipe
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PendingRecipeRepository : JpaRepository<PendingRecipe, Long>
