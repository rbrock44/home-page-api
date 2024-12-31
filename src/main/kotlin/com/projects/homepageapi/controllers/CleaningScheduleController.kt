package com.projects.homepageapi.controllers

import com.projects.homepageapi.angularOrigin
import com.projects.homepageapi.maxAge
import com.projects.homepageapi.models.Meeting
import com.projects.homepageapi.services.CleaningScheduleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("cleaning-schedule")
@CrossOrigin(origins = [angularOrigin], maxAge = maxAge)
class CleaningScheduleController(
    @Autowired private val service: CleaningScheduleService
) {
    @GetMapping("/week")
    fun getWeeklySchedule(
        @RequestParam startDate: String = ""
    ): List<Meeting> {
        return service.getMeetingsByWeek(startOfWeek = startDate)
    }

    @GetMapping()
    fun getAllSchedule(): List<Meeting> {
        return service.getAllMeetings()
    }

    @PostMapping("/add")
    fun addMeeting(
        @RequestBody meeting: Meeting
    ): Meeting {
        return service.addMeeting(meeting)
    }

    @PostMapping("/edit")
    fun updateMeeting(
        @RequestBody meeting: Meeting
    ): Meeting {
       return service.updateMeeting(meeting)
    }

    @DeleteMapping("")
    fun deleteMeeting(
        @RequestParam id: Int = -1
    ): Boolean {
        return service.deleteMeeting(id = id)
    }
}
