package com.projects.homepageapi.services

import com.projects.homepageapi.models.MediaFile
import com.projects.homepageapi.repositories.MediaFileRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.net.URL

@Service
class HomeMediaService(
    private val mediaFileRepository: MediaFileRepository
) {
    @Scheduled(cron = "0 6 * * * *")
    fun getMediaFilesFromRepo() {
        val rawFileUrl = "https://raw.githubusercontent.com/rbrock44/home-page-media-file/master/media.txt"

        val lines: List<String> = URL(rawFileUrl).openStream().bufferedReader().use { it.readLines() }
        val mediaFiles = lines.map { MediaFile(0, it) }

        if (lines.isNotEmpty()) {
            mediaFileRepository.deleteAll()
            mediaFileRepository.saveAll(mediaFiles)
        }
    }

    fun getFilenamesThatContain(criteria: String): List<String> {
        return if (criteria.trim().isEmpty()) {
            mediaFileRepository.findAll().map { it.name }.sorted()
        } else {
            val formattedCriteria = "%" + criteria.trim().uppercase().replace(" ", "%") + "%"
            mediaFileRepository.findByCriteria(formattedCriteria).sorted()
        }
    }
}
