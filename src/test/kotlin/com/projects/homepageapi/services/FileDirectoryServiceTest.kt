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
