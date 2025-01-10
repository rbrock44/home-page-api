package com.projects.homepageapi.services

import com.projects.homepageapi.models.FlashCardData
import kotlinx.serialization.json.Json
import org.eclipse.jgit.api.Git
import org.springframework.stereotype.Service
import java.nio.file.Files

@Service
class FlashCardsService(
    private val fileService: FileService,
) {
    fun getFlashCardFileFromRepo(): FlashCardData {
        val repoUrl = "https://github.com/rbrock44/flash-cards-data"
        val filePath = "flash-card-data.json"
        val cloneDirectory = Files.createTempDirectory("git-clone")
        Git.cloneRepository()
            .setURI(repoUrl)
            .setDirectory(cloneDirectory.toFile())
            .call()

        val fileUrl = cloneDirectory.resolve(filePath).toString()

        val jsonText = fileService.getTextFromFile(fileUrl)

        val flashCards = Json.decodeFromString<FlashCardData>(jsonText)

        return flashCards
    }
}
