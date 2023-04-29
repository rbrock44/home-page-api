package com.projects.homepageapi.services

import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class LocalDateTimeService {
    fun now(): LocalDateTime {
        return LocalDateTime.now()
    }
}
