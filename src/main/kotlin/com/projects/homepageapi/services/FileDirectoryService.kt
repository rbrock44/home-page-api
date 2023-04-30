package com.projects.homepageapi.services

import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import java.io.File

@Service
class FileDirectoryService {
    fun writeToFile(outputList: List<String>, path: String) {
        val resource = ClassPathResource(path)
        File(resource.uri).writeText(outputList.joinToString(separator = "\n"))
    }

    fun getLinesFromFile(file: String): List<String> {
        val resource = ClassPathResource(file)
        return File(resource.uri).readLines()
    }
}
