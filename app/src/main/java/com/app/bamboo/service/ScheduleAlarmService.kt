package com.app.bamboo.service

import android.content.Context
import com.app.bamboo.domain.usecases.ScheduleNextNotificationUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ScheduleAlarmService @Inject constructor(
    private val scheduleNextNotificationUseCase: ScheduleNextNotificationUseCase,
) {
    fun scheduleNextNotification(medications: List<String>) {
        CoroutineScope(Dispatchers.IO).launch {
            scheduleNextNotificationUseCase(medications)
        }
    }
}