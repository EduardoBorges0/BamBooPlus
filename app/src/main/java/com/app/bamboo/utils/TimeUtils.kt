package com.app.bamboo.utils

import java.time.Duration
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Date

object TimeUtils {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    fun formattedLocalDate(time: String): LocalTime{
        return LocalTime.parse(time, formatter)
    }
}