package com.projects.homepageapi.services

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.MockitoAnnotations
import java.io.File

internal class FileDirectoryServiceTest {
    @InjectMocks
    lateinit var service: FileDirectoryService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `should get file names from all directories inside source`() {
        val source = "src/test/resources"
        val expected = listOf(
            "espn-nba.html",
            "espn-nfl.html",
            "example.txt",
            "mma.html",
            "example.txt",
            "example2.txt",
            "another-example.txt",
        )
        val result = service.getFiles(source)
        assertEquals(expected, result)
    }

    @Test
    fun `should return empty list when given bad path`() {
        val expected = emptyList<String>()
        val result = service.getFiles("bad-path")
        assertEquals(expected, result)
    }

    @Test
    fun `should get file names from multiple directories`() {
        val sources = listOf(
            "src/test/resources/example",
            "src/test/resources/example2"
        )
        val expected = listOf(
            "example.txt",
            "example2.txt",
            "another-example.txt",
        )
        val result = service.getFiles(sources)
        assertEquals(expected, result)
    }

    @Test
    fun `should write out to file`() {
        val list = listOf(
            "1",
            "2",
            "3"
        )
        val path = "example.txt"
        File(path).writeText("")

        service.writeToFile(list, path)

        val lines = service.getLinesFromFile(path)
        assertEquals(list, lines)
    }
}
