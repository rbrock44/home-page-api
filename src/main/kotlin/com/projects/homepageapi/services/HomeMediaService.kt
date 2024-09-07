package com.projects.homepageapi.services

import com.projects.homepageapi.models.MediaFile
import com.projects.homepageapi.repositories.MediaFileRepository
import org.eclipse.jgit.api.Git
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.nio.file.Files

@Service
class HomeMediaService(
    private val fileService: FileService,
    private val mediaFileRepository: MediaFileRepository
) {
    @Scheduled(cron = "0 6 * * * *")
    fun getMediaFilesFromRepo() {
        val repoUrl = "https://github.com/rbrock44/home-page-media-file"
        val filePath = "media.txt"
        val cloneDirectory = Files.createTempDirectory("git-clone")
        Git.cloneRepository()
            .setURI(repoUrl)
            .setDirectory(cloneDirectory.toFile())
            .call()

        val fileUrl = cloneDirectory.resolve(filePath).toString()

        val lines = fileService.getLinesFromFile(fileUrl)
        val mediaFiles = lines.map { MediaFile(0, it) }

        if (lines.isNotEmpty()) {
            mediaFileRepository.deleteAll()
            mediaFileRepository.saveAll(mediaFiles)
        }
    }

    fun getFilenamesThatContain(criteria: String): List<String> {
        val mediaFiles: List<MediaFile> = mediaFileRepository.findAll()
        val filenames = mutableListOf<String>()
        if (criteria.trim() == "") {
            filenames.addAll(mediaFiles.map { it.name })
        } else {
            mediaFiles.forEach {
                var hasEveryCriteria = true
                criteria.split(" ").forEach { item ->
                    hasEveryCriteria = hasEveryCriteria && it.name.uppercase().contains(item.trim().uppercase())
                }

                if (hasEveryCriteria)
                    filenames.add(it.name)
            }
        }

        return filenames.sorted()
    }
}
