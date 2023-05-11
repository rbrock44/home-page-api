package com.projects.homepageapi.services

import org.springframework.stereotype.Service
import java.io.File

@Service
class FileDirectoryService {
    fun writeToFile(outputList: List<String>, path: String) {
        getFile(path).writeText(outputList.joinToString(separator = "\n"))
    }

    fun getLinesFromFile(path: String): List<String> {
        return getFile(path).readLines()
    }

    private fun getFile(path: String): File {
        val file = File(path)
        file.createNewFile()
        return file
    }
}
