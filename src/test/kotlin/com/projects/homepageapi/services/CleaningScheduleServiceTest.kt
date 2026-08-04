package com.projects.homepageapi.services

import com.projects.homepageapi.models.Meeting
import com.projects.homepageapi.repositories.MeetingRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class CleaningScheduleServiceTest {
    @Mock
    lateinit var meetingRepository: MeetingRepository

    @InjectMocks
    lateinit var service: CleaningScheduleService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `should replace imported meetings with fresh inserts`() {
        service.replaceMeetings(
            listOf(
                "2|2026-04-21|18:00|19:00|Kitchen|Ryan",
                ""
            )
        )

        verify(meetingRepository).deleteAllInBatch()

        val meetingsCaptor = argumentCaptor<Iterable<Meeting>>()
        verify(meetingRepository).saveAll(meetingsCaptor.capture())

        assertEquals(
            listOf(
                Meeting(
                    date = "2026-04-21",
                    startTime = "18:00",
                    endTime = "19:00",
                    title = "Kitchen",
                    person = "Ryan",
                    hasBeenPaid = false
                )
            ),
            meetingsCaptor.firstValue.toList()
        )
    }

    @Test
    fun `should ignore client provided id when adding meeting`() {
        val meeting = Meeting(
            id = 7,
            date = "2026-04-21",
            startTime = "18:00",
            endTime = "19:00",
            title = "Kitchen",
            person = "Ryan",
            hasBeenPaid = true
        )

        service.addMeeting(meeting)

        val meetingCaptor = argumentCaptor<Meeting>()
        verify(meetingRepository).save(meetingCaptor.capture())

        assertEquals(0, meetingCaptor.firstValue.id)
        assertEquals(false, meetingCaptor.firstValue.hasBeenPaid)
        assertEquals(meeting.copy(id = 0, hasBeenPaid = false), meetingCaptor.firstValue)
    }

    @Test
    fun `should query monday through friday when given a start of week date`() {
        val expected = listOf(Meeting(date = "2026-04-21", title = "Kitchen"))
        whenever(meetingRepository.findByDateIn(any())).thenReturn(expected)

        val result = service.getMeetingsByWeek("2026-04-20")

        val datesCaptor = argumentCaptor<List<String>>()
        verify(meetingRepository).findByDateIn(datesCaptor.capture())

        assertEquals(
            listOf("2026-04-20", "2026-04-21", "2026-04-22", "2026-04-23", "2026-04-24"),
            datesCaptor.firstValue
        )
        assertEquals(expected, result)
    }

    @Test
    fun `should return empty list for an unparseable start of week date`() {
        val result = service.getMeetingsByWeek("not-a-date")

        assertEquals(emptyList<Meeting>(), result)
    }
}