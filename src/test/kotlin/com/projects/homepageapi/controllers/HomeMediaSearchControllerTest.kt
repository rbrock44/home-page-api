package com.projects.homepageapi.controllers

import com.projects.homepageapi.services.HomeMediaService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify

internal class HomeMediaSearchControllerTest {
    @Mock
    lateinit var service: HomeMediaService

    @InjectMocks
    lateinit var controller: HomeMediaSearchController

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @ParameterizedTest
    @CsvSource("1", "abc", "gbh")
    fun `should return filenames that match criteria`(criteria: String) {
        controller.getFilenames(criteria)
        verify(service).getFilenamesThatContain(criteria)

    }
}
