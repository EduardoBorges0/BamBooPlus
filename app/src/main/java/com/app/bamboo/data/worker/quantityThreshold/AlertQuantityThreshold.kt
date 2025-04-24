package com.app.bamboo.data.worker.quantityThreshold

import android.content.Context
import android.util.Log
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

fun alertQuantityThreshold(context: Context) {
    val workRequest = PeriodicWorkRequestBuilder<QuantityThresholdWorker>(2, TimeUnit.HOURS).build()
    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        "quantityThresholdWork",
        ExistingPeriodicWorkPolicy.KEEP,
        workRequest
    )
}