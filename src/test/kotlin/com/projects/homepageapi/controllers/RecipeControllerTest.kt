package com.projects.homepageapi.controllers

import com.projects.homepageapi.models.PendingRecipe
import com.projects.homepageapi.repositories.PendingRecipeRepository
import com.projects.homepageapi.services.GithubDispatchService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.http.HttpStatus

internal class RecipeControllerTest {
    @Mock
    lateinit var pendingRecipeRepository: PendingRecipeRepository

    @Mock
    lateinit var githubDispatchService: GithubDispatchService

    @InjectMocks
    lateinit var controller: RecipeController

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `should save submitted payload as a pending recipe`() {
        val saved = PendingRecipe(id = 1, jsonPayload = "{name=Chili}")
        whenever(pendingRecipeRepository.save(any<PendingRecipe>())).thenReturn(saved)

        val response = controller.createPendingRecipe(mapOf("name" to "Chili"))

        val recipeCaptor = argumentCaptor<PendingRecipe>()
        verify(pendingRecipeRepository).save(recipeCaptor.capture())
        verify(githubDispatchService).dispatchRecipeSubmitted()

        assertEquals(0, recipeCaptor.firstValue.id)
        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertEquals(saved, response.body)
    }

    @Test
    fun `should delete a pending recipe that exists`() {
        whenever(pendingRecipeRepository.existsById(1L)).thenReturn(true)

        val response = controller.deletePendingRecipe(1L)

        verify(pendingRecipeRepository).deleteById(1L)
        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
    }

    @Test
    fun `should not delete and return not found when pending recipe is missing`() {
        whenever(pendingRecipeRepository.existsById(1L)).thenReturn(false)

        val response = controller.deletePendingRecipe(1L)

        verify(pendingRecipeRepository, org.mockito.kotlin.never()).deleteById(any<Long>())
        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
    }

    @Test
    fun `should get all pending recipes`() {
        val recipes = listOf(PendingRecipe(id = 1, jsonPayload = "{name=Chili}"))
        whenever(pendingRecipeRepository.findAll()).thenReturn(recipes)

        val response = controller.getAllPendingRecipes()

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(recipes, response.body)
    }
}
