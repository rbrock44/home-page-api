package com.projects.homepageapi.services

import com.projects.homepageapi.mediaFilepath
import com.projects.homepageapi.models.Meeting
import org.springframework.stereotype.Service
import java.nio.file.Files
import org.eclipse.jgit.api.Git

@Service
class CleaningScheduleService(
    private val fileService: FileService
) {
    // we either manage the meetings in text files broken up however or we make an in memory h2 database with spring

    // text files option
        // figure out how to break up (by week, year or just 1 file)
        // not as fast
        // lots of lambda expressions
        // pre-populate database on startup (from github repo)

    // h2 in memory database option
        // we write sql queries instead of loops over files, meetings or lines in files
        // indexes should make it faster
        // pre-populate database on startup (from github repo)

    fun getMeetingsFromRepo() {
        // TODO: change to new values
        val repoUrl = "https://github.com/rbrock44/home-page-media-file"

        //instead of one filePath, we will need to know the start date 20240805, and calculate every value between then and now (+ 1 month, TBD how far in the future), seeing if those file exists
        // separate thought, if we saved the meetings by year that might be easier than weekly
        val filePath = "media.txt"
        val cloneDirectory = Files.createTempDirectory("git-clone")
        Git.cloneRepository()
            .setURI(repoUrl)
            .setDirectory(cloneDirectory.toFile())
            .call()

        val fileUrl = cloneDirectory.resolve(filePath).toString()

        val lines = fileService.getLinesFromFile(fileUrl)
        if (lines.isNotEmpty())
            fileService.writeToFile(lines, mediaFilepath)
    }

    fun getAllMeetings(): List<Meeting> {
        // TODO: get all meetings (this might need to factor in year, or loop through many year, idk)
        return emptyList()
    }

    fun getMeetingsByWeek(startOfWeek: String): List<Meeting> {
        // TODO: get meetings by week
        // TODO: default to Monday of current week in YYYYMMDD format string
        // also handle if start of week is not a monday
        return emptyList()
    }

    fun deleteMeeting(id: Int): Boolean {
        // TODO: attempt to delete, if found and deleted return true (deleted) else false
        return false
    }

    fun updateMeeting(meeting: Meeting) {

    }

    fun addMeeting(meeting: Meeting) {

    }
}
