package com.app.bamboo.data.worker

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

fun deleteAllHistoryMedication(context: Context) {
    val workRequest = OneTimeWorkRequestBuilder<WeeklyCleanupWorker>(
    ).build()

    WorkManager.getInstance(context).enqueue(
        workRequest
    )
}

