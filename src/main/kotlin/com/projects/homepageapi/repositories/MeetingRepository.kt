package com.projects.homepageapi.repositories

import com.projects.homepageapi.models.Meeting
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MeetingRepository : JpaRepository<Meeting, Int> {
    fun findByDate(date: String): List<Meeting>
}
