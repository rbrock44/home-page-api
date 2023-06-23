package com.projects.homepageapi.services

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class HomeMediaServiceTest {
    @Mock
    lateinit var fileService: FileService

    @InjectMocks
    lateinit var service: HomeMediaService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    private val path = "files.txt"

    @Test
    fun `should get files from the two sources`() {
        service.saveFilenames()
    }

    @Test
    fun `should write files to source`() {
        val expected = listOf(
            "list",
            "another list"
        )
        service.saveFilenames()
        verify(fileService).writeToFile(expected, path)
    }

    @Test
    fun `should not write empty file list to source`() {
        val expected = emptyList<String>()
        service.saveFilenames()
        verify(fileService, never()).writeToFile(expected, path)
    }

    @Test
    fun `should get filenames that contain criteria`() {
        val list = listOf(
            "lisT",
            "another one",
            "iSt",
            "example"
        )
        val expected = listOf(
            "iSt",
            "lisT"
        )
        whenever(fileService.getLinesFromFile(path)).thenReturn(list)
        val result = service.getFilenamesThatContain("st")
        assertEquals(expected, result)
    }

    @Test
    fun `should get filenames that contain each word in criteria`() {
        val list = listOf(
            "The Lord of the",
            "The Lord Underpass",
            "Lord Underpass Who",
            "Digging into the Lord",
            "Of The Keep"
        )
        val expected = listOf(
            "Of The Keep",
            "The Lord of the"
        )
        whenever(fileService.getLinesFromFile(path)).thenReturn(list)
        val result = service.getFilenamesThatContain("The Of")
        assertEquals(expected, result)
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
        whenever(fileService.getLinesFromFile(path)).thenReturn(expected)
        val result = service.getFilenamesThatContain(" ")
        assertEquals(expected, result)
    }

    @Test
    fun `should scrap filenames when none are found in file`() {
        val expected = emptyList<String>()
        whenever(fileService.getLinesFromFile(path)).thenReturn(expected)
        val result = service.getFilenamesThatContain(" ")
        assertEquals(expected, result)
    }
}
