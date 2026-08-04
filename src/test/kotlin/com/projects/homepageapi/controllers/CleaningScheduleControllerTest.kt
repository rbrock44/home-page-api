package com.projects.homepageapi.controllers

import com.projects.homepageapi.models.Meeting
import com.projects.homepageapi.services.CleaningScheduleService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

internal class CleaningScheduleControllerTest {
    @Mock
    lateinit var service: CleaningScheduleService

    @InjectMocks
    lateinit var controller: CleaningScheduleController

    private val meeting = Meeting(
        id = 1,
        date = "2026-04-21",
        startTime = "18:00",
        endTime = "19:00",
        title = "Kitchen",
        person = "Ryan"
    )

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `should get weekly schedule for the given start date`() {
        whenever(service.getMeetingsByWeek("2026-04-20")).thenReturn(listOf(meeting))

        val result = controller.getWeeklySchedule("2026-04-20")

        assertEquals(listOf(meeting), result)
    }

    @Test
    fun `should get all schedule`() {
        whenever(service.getAllMeetings()).thenReturn(listOf(meeting))

        val result = controller.getAllSchedule()

        assertEquals(listOf(meeting), result)
    }

    @Test
    fun `should delegate meeting creation to service`() {
        whenever(service.addMeeting(meeting)).thenReturn(meeting)

        val result = controller.addMeeting(meeting)

        assertEquals(meeting, result)
    }

    @Test
    fun `should delegate meeting update to service`() {
        whenever(service.updateMeeting(meeting)).thenReturn(meeting)

        val result = controller.updateMeeting(meeting)

        assertEquals(meeting, result)
    }

    @Test
    fun `should delegate meeting deletion to service`() {
        whenever(service.deleteMeeting(1)).thenReturn(true)

        val result = controller.deleteMeeting(1)

        assertEquals(true, result)
        verify(service).deleteMeeting(1)
    }
}
