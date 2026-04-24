package com.projects.homepageapi.services

import com.projects.homepageapi.models.MediaFile
import com.projects.homepageapi.repositories.MediaFileRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class HomeMediaServiceTest {
    @Mock
    lateinit var mediaFileRepository: MediaFileRepository

    @InjectMocks
    lateinit var service: HomeMediaService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `should get filenames that contain criteria`() {
        val expected = listOf(
            "iSt",
            "lisT"
        )
        whenever(mediaFileRepository.findByCriteria("%ST%")).thenReturn(expected)
        val result = service.getFilenamesThatContain("st")
        assertEquals(expected, result)
        verify(mediaFileRepository).findByCriteria("%ST%")
    }

    @Test
    fun `should get filenames that contain each word in criteria`() {
        val expected = listOf(
            "Of The Keep",
            "The Lord of the"
        )
        whenever(mediaFileRepository.findByCriteria("%THE%OF%")).thenReturn(expected)
        val result = service.getFilenamesThatContain("The Of")
        assertEquals(expected, result)
        verify(mediaFileRepository).findByCriteria("%THE%OF%")
    }

    @Test
    fun `should get all filenames when search is blank`() {
        val expected = listOf(
            "Digging into the Lord",
            "Lord Underpass Who",
            "Of The Keep",
            "The Lord Underpass",
            "The Lord of the"
        )
        whenever(mediaFileRepository.findAll()).thenReturn(expected.map { MediaFile(name = it) })
        val result = service.getFilenamesThatContain(" ")
        assertEquals(expected, result)
        verify(mediaFileRepository).findAll()
    }

    @Test
    fun `should scrap filenames when none are found in file`() {
        val expected = emptyList<String>()
        whenever(mediaFileRepository.findAll()).thenReturn(expected.map { MediaFile(name = it) })
        val result = service.getFilenamesThatContain(" ")
        assertEquals(expected, result)
        verify(mediaFileRepository).findAll()
    }
}
