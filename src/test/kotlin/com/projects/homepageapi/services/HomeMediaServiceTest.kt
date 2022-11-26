package com.projects.homepageapi.services

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
    lateinit var fileDirectoryService: FileDirectoryService

    @InjectMocks
    lateinit var service: HomeMediaService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    private val sources = listOf(
        "//10.0.0.50/usbc/Media/TV Shows",
        "//10.0.0.50/usbc/Media/Movies"
    )

    private val path = "src/main/resources/files.txt"

    @Test
    fun `should get files from the two sources`() {
        service.saveFilenames()
        verify(fileDirectoryService).getFiles(sources)
    }

    @Test
    fun `should write files to source`() {
        val expected = listOf(
            "list",
            "another list"
        )
        whenever(fileDirectoryService.getFiles(sources)).thenReturn(expected)
        service.saveFilenames()
        verify(fileDirectoryService).writeToFile(expected, path)
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
        whenever(fileDirectoryService.getLinesFromFile(path)).thenReturn(list)
        val result = service.getFilenamesThatContain("st")
        assertEquals(expected, result)
    }
}
