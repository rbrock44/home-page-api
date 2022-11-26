package com.projects.homepageapi.services

import org.springframework.stereotype.Repository
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter


@Repository
class DateService(
    private var localDateTimeService: LocalDateTimeService
) {
    fun getCurrentDate(format: String = nbaFormat): String {
        val current = localDateTimeService.now()

        val formatter = DateTimeFormatter.ofPattern(format)
        return current.format(formatter)
    }

    fun isAfterOrEqualToToday(value: String, dateFormat: String): Boolean {
        val sdf = SimpleDateFormat(dateFormat)
        val parse1 = sdf.parse(this.getCurrentDate(format = dateFormat))
        val parse2 = sdf.parse(value)
        val equal = parse1.equals(parse2)
        val after = parse2.after(parse1)
        return after || equal
    }

    companion object {
        val nbaFormat = "EEEE, MMMM dd"
        val nflFormat = "EEEE, MMMM dd"
        val mmaFormat = "MMMM dd, yyyy"
    }
}
