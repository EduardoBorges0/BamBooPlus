package com.app.bamboo.domain.usecases

import android.content.Context
import com.app.bamboo.presentation.notifications.scheduleNotification
import java.time.LocalTime
import javax.inject.Inject

class ScheduleNextNotificationUseCase @Inject constructor() {
    operator fun invoke(
        context: Context,
        medications: List<String>,
    ) {
        val now = LocalTime.now()
        val times = medications.map { LocalTime.parse(it) }
        val nextTime = times.firstOrNull { it.isAfter(now) }
            ?: times.firstOrNull()
                ?.plusHours(24)
        nextTime?.let {
            scheduleNotification(context, it.hour, it.minute)
        }
    }
}