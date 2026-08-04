package com.projects.homepageapi.controllers

import com.projects.homepageapi.models.FlashCardData
import com.projects.homepageapi.services.FlashCardsService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

internal class FlashCardsControllerTest {
    @Mock
    lateinit var flashCardsService: FlashCardsService

    @InjectMocks
    lateinit var controller: FlashCardsController

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `should get flash card data`() {
        val expected = FlashCardData(categories = emptyList())
        whenever(flashCardsService.getFlashCardFileFromRepo()).thenReturn(expected)

        assertEquals(expected, controller.get())
    }
}
