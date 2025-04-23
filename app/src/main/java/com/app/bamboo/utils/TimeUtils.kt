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
    fun parseToLocalDateTime(date: String, time: String): LocalDateTime {
        val formatters = listOf(
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"),
            DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm")
        )
        val dateTimeString = "$date $time"
        for (formatter in formatters) {
            try {
                return LocalDateTime.parse(dateTimeString, formatter)
            } catch (e: DateTimeParseException) {
                // tenta o próximo formato
            }
        }
        throw IllegalArgumentException("Formato de data e hora inválido: $dateTimeString")
    }

}