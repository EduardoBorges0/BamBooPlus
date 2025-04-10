package com.app.bamboo.utils

import java.time.Duration
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

object TimeUtils {
    private val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    private val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    fun formattedLocalTime(time: String): LocalTime{
        return LocalTime.parse(time, timeFormatter)
    }
    fun formattedLocalDate(date: String): LocalDate{
        return LocalDate.parse(date, dateFormatter)
    }
}