package com.projects.homepageapi.services

import org.springframework.stereotype.Repository
import java.time.LocalDateTime


@Repository
class LocalDateTimeService {
    fun now(): LocalDateTime {
        return LocalDateTime.now()
    }
}
