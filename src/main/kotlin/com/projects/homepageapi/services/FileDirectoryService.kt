package com.projects.homepageapi.services

import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import java.io.File

@Service
class FileDirectoryService {
    fun getFiles(directories: List<String>): List<String> {
        val fileList = mutableListOf<String>()
        val dirList = mutableListOf<String>()
        dirList.addAll(directories)
        while (dirList.isNotEmpty()) {
            val directory = dirList[0]
            val files = File(directory).list { dir, name -> !File(dir, name).isDirectory}
            files?.forEach {
                fileList.add(it)
            }
            val newDirectories = File(directory).list { dir, name -> File(dir, name).isDirectory}
            newDirectories?.forEach {
                dirList.add("$directory/$it")
            }
            dirList.removeAt(0)
        }
        return fileList
    }

    fun getFiles(directory: String): List<String> {
        return getFiles(listOf(directory))
    }

    fun writeToFile(outputList: List<String>, path: String) {
        val resource = ClassPathResource(path)
        File(resource.uri).writeText(outputList.joinToString(separator = "\n"))
    }

    fun getLinesFromFile(file: String): List<String> {
        val resource = ClassPathResource(file)
        return File(resource.uri).readLines()
    }

    fun isWindows(): Boolean {
        return System.getProperty("os.name").uppercase().contains("WIN")
    }
}
