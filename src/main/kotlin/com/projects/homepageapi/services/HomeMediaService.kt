package com.projects.homepageapi.services

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Repository

@Repository
class HomeMediaService(
    private val fileDirectoryService: FileDirectoryService
) {
    private val sources = listOf(
        """\\10.0.0.50\usbc\TV Shows""",
        """\\10.0.0.50\usbc\Movies"""
    )
    private val outputFile = "files.txt"

    @Scheduled(cron = "0 6 * * *")
    fun saveFilenames() {
        val files = fileDirectoryService.getFiles(sources)
        if (files.isNotEmpty())
            fileDirectoryService.writeToFile(files, outputFile)
    }

    fun getFilenamesThatContain(criteria: String): List<String> {
        val list = fileDirectoryService.getLinesFromFile(outputFile)
        val filenames = mutableListOf<String>()
        if (criteria.trim() == "") {
            filenames.addAll(list)
        } else {
            list.forEach {
                var hasEveryCriteria = true
                criteria.split(" ").forEach { item ->
                    hasEveryCriteria = hasEveryCriteria && it.uppercase().contains(item.trim().uppercase())
                }

                if (hasEveryCriteria)
                    filenames.add(it)
            }
        }

        return filenames.sorted()
    }
}
