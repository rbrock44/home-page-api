package com.projects.homepageapi.configs

import com.projects.homepageapi.services.CleaningScheduleService
import com.projects.homepageapi.services.HomeMediaService
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class StartupConfig {

    @Bean
    fun initMeetingDatabase(service: CleaningScheduleService): CommandLineRunner {
        return CommandLineRunner {
            service.getMeetingsFromRepo()
        }
    }

    @Bean
    fun initMediaFileDatabase(service: HomeMediaService): CommandLineRunner {
        return CommandLineRunner {
            service.getMediaFilesFromRepo()
        }
    }
}
