package com.projects.homepageapi.controllers

import com.projects.homepageapi.models.Event
import com.projects.homepageapi.services.ScrapingHelperService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

internal class GdqControllerTest {
    @Mock
    lateinit var service: ScrapingHelperService

    @InjectMocks
    lateinit var controller: GdqController

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `should get upcoming gdq event`() {
        val expected = Event(name = "GDQ", dates = "2026-04-21", url = "url")
        whenever(service.parseGdqWebsite()).thenReturn(expected)

        assertEquals(expected, controller.getUpcoming())
    }
}
