package com.projects.homepageapi.services

import com.projects.homepageapi.models.Meeting
import com.projects.homepageapi.repositories.MeetingRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.net.URL
import java.time.LocalDate
import java.time.format.DateTimeParseException

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
        val monday = try {
            LocalDate.parse(startOfWeek)
        } catch (e: DateTimeParseException) {
            return emptyList()
        }

        val weekDates = (0L..4L).map { monday.plusDays(it).toString() }
        return meetingRepository.findByDateIn(weekDates)
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
        val meetingToSave = meeting.copy(id = 0, hasBeenPaid = false)
        val savedMeeting: Meeting? = meetingRepository.save(meetingToSave)
        return savedMeeting ?: meetingToSave
    }
}
