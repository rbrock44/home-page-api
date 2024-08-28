package com.projects.homepageapi.configs

import com.projects.homepageapi.services.CleaningScheduleService
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class StartupConfig {

    @Bean
    fun initDatabase(service: CleaningScheduleService): CommandLineRunner {
        return CommandLineRunner {
            service.getMeetingsFromRepo()
        }
    }
}
