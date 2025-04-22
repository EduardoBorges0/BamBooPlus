package com.app.bamboo.utils

import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Date
import java.util.Locale

object TimeUtils {
    private val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    fun formattedLocalDateTime(time: String): LocalTime {
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        return LocalTime.parse(time, formatter)
    }

    fun formattedLocalDate(date: String): LocalDate {
        val isoFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val dateFormat1 = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val dateFormat2 = DateTimeFormatter.ofPattern("MM/dd/yyyy")

        return try {
            LocalDate.parse(date, isoFormat)
        } catch (e1: DateTimeParseException) {
            try {
                LocalDate.parse(date, dateFormat1)
            } catch (e2: DateTimeParseException) {
                LocalDate.parse(date, dateFormat2)
            }
        }
    }

}