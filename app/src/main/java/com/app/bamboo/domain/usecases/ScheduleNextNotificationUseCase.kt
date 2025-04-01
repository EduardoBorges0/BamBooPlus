package com.app.bamboo.domain.usecases

import android.content.Context
import android.util.Log
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.app.bamboo.domain.usecases.workers.MedicationAlarmWorker
import dagger.hilt.android.EntryPointAccessors
import java.time.LocalTime
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ScheduleNextNotificationUseCase @Inject constructor(
    private val workManager: WorkManager,
) {
    operator fun invoke(
        medications: List<String>,
    ) {
        val data = workDataOf("medications" to medications.toTypedArray())

        val workRequest = OneTimeWorkRequestBuilder<MedicationAlarmWorker>()
            .setInputData(data)
            .build()

        workManager.enqueue(workRequest)

    }
}
