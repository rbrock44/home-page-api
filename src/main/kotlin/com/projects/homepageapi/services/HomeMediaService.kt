package com.projects.homepageapi.services

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class HomeMediaService(
    private val fileDirectoryService: FileDirectoryService,
    private val scraperService: ScrapingHelperService
) {
    private val outputFile = "media.txt"

    @Scheduled(cron = "0 6 * * *")
    fun saveFilenames() {
        val lines = scraperService.parseMediaFile();
        if (lines.isNotEmpty())
            fileDirectoryService.writeToFile(lines, outputFile)
    }

    fun getFilenamesThatContain(criteria: String): List<String> {
        var loopCount = 0
        var list: List<String> = emptyList()

        while (loopCount < 2 && list.isEmpty()) {
            list = fileDirectoryService.getLinesFromFile(outputFile)
            if (list.isEmpty()) {
                saveFilenames()
            }
            loopCount++
        }

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
