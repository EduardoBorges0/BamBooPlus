package com.app.bamboo.domain.usecases

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.app.bamboo.domain.usecases.workers.MedicationAlarmWorker
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ScheduleNextNotificationUseCase @Inject constructor(
    private val workManager: WorkManager,
) {
    operator fun invoke(
        context: Context,
        medications: List<String>,
    ) {
        val data = workDataOf("medications" to medications.toTypedArray())

        val workRequest = PeriodicWorkRequestBuilder<MedicationAlarmWorker>(15, TimeUnit.MINUTES)
            .setInputData(data)
            .build()

        workManager.enqueueUniquePeriodicWork(
            "medication_alarm_work",
            ExistingPeriodicWorkPolicy.UPDATE,
            workRequest
        )
    }
}
