package com.projects.homepageapi.controllers

import com.projects.homepageapi.services.HomeMediaService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify

internal class MediaControllerTest {
    @Mock
    lateinit var mediaService: HomeMediaService

    @InjectMocks
    lateinit var controller: MediaController

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `should trigger media refresh from repo`() {
        controller.updateMedia()

        verify(mediaService).getMediaFilesFromRepo()
    }
}
