package com.projects.homepageapi.services

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import java.time.LocalDateTime

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
}
