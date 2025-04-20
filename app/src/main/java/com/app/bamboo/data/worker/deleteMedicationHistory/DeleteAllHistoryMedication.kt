package com.app.bamboo.data.worker.deleteMedicationHistory

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import java.util.Calendar
import java.util.concurrent.TimeUnit

fun deleteAllHistoryMedication(context: Context) {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.HOUR_OF_DAY, 23)
    calendar.set(Calendar.MINUTE, 55)
    calendar.set(Calendar.SECOND, 0)
    if (calendar.timeInMillis < System.currentTimeMillis()) {
        calendar.add(Calendar.DAY_OF_YEAR, 1)
    }
    val delay = calendar.timeInMillis - System.currentTimeMillis()

    val workRequest = OneTimeWorkRequestBuilder<WeeklyCleanupWorker>()
        .setInitialDelay(delay, TimeUnit.MILLISECONDS)
        .build()

    // Enqueue the work
    WorkManager.getInstance(context).enqueueUniqueWork(
        "daily_cleanup_worker",
        ExistingWorkPolicy.REPLACE,
        workRequest
    )
}

