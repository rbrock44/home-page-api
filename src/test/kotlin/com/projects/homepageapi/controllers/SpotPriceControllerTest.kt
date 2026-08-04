package com.projects.homepageapi.controllers

import com.projects.homepageapi.models.SpotPrices
import com.projects.homepageapi.services.SpotPriceService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

internal class SpotPriceControllerTest {
    @Mock
    lateinit var service: SpotPriceService

    @InjectMocks
    lateinit var controller: SpotPriceController

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `should get spot prices`() {
        val expected = SpotPrices(gold = 2000.0, silver = 25.0)
        whenever(service.getSpotPrices()).thenReturn(expected)

        assertEquals(expected, controller.getSpotPrices())
    }
}
