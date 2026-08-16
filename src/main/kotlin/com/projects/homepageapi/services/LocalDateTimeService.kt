package com.projects.homepageapi.services

import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

@Service
class LocalDateTimeService {
    fun now(): LocalDateTime {
        return LocalDateTime.now()
    }

    fun today(zoneId: ZoneId): LocalDate {
        return LocalDate.now(zoneId)
    }
}
