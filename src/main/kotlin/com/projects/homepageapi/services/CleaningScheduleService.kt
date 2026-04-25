package com.projects.homepageapi.services

import com.projects.homepageapi.models.Meeting
import com.projects.homepageapi.repositories.MeetingRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.net.URL

@Service
class CleaningScheduleService(
    private val meetingRepository: MeetingRepository
) {
    @Transactional
    fun getMeetingsFromRepo() {
        val rawFileUrl = "https://raw.githubusercontent.com/rbrock44/cleaning-schedule-database/master/meetings.txt"

        val lines: List<String> = URL(rawFileUrl).openStream().bufferedReader().use { it.readLines() }

        replaceMeetings(lines)
    }

    internal fun replaceMeetings(lines: List<String>) {
        val meetings = lines
            .asSequence()
            .filter { it.isNotBlank() }
            .map { Meeting.fromLine(it).copy(id = 0) }
            .toList()

        meetingRepository.deleteAllInBatch()
        meetingRepository.saveAll(meetings)
    }

    fun getAllMeetings(): List<Meeting> = meetingRepository.findAll()

    fun getMeetingsByWeek(startOfWeek: String): List<Meeting> {
        // TODO: Implement logic for filtering meetings by week
        // make start of week a monday then get the rest of the 4 days
        // default to this monday YYYY-MM-DD
        return meetingRepository.findByDate(startOfWeek)
    }

    fun deleteMeeting(id: Int): Boolean {
        return if (meetingRepository.existsById(id)) {
            meetingRepository.deleteById(id)
            true
        } else {
            false
        }
    }

    fun updateMeeting(meeting: Meeting): Meeting {
        return meetingRepository.save(meeting)
    }

    fun addMeeting(meeting: Meeting): Meeting {
        val meetingToSave = meeting.copy(id = 0)
        val savedMeeting: Meeting? = meetingRepository.save(meetingToSave)
        return savedMeeting ?: meetingToSave
    }
}
