package com.projects.homepageapi.services

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

internal class DateServiceTest {
    @Mock
    lateinit var localDateTimeService: LocalDateTimeService

    @InjectMocks
    lateinit var service: DateService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @ParameterizedTest
    @CsvSource(
        "Friday, November 25, 2022/ true",
        "Thursday, October 20, 2021/ true",
        "Tuesday, July 2, 2002/ false",
        delimiterString = "/"
    ) fun `should check if date is after or equal to today`(date: String, expected: Boolean) {
        whenever(localDateTimeService.now()).thenReturn(
            LocalDateTime.of(2021, 10, 20, 0, 0)
        )
        assertEquals(expected, service.isAfterOrEqualToToday(date, DateService.nbaFormat))
    }

    @Test
    fun `should delegate today to local date time service`() {
        val zone = ZoneId.of("America/New_York")
        val expected = LocalDate.of(2026, 8, 16)
        whenever(localDateTimeService.today(zone)).thenReturn(expected)

        assertEquals(expected, service.today(zone))
    }
}
