package com.app.bamboo.domain.notifications

import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.app.bamboo.data.worker.MedicationAlarmWorker
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ScheduleNextNotificationUseCase @Inject constructor(
    private val workManager: WorkManager,
) {
    operator fun invoke(
        medications: List<String>?,
    ) {
        val data = workDataOf("medications" to medications?.toTypedArray())

        val workRequest = PeriodicWorkRequestBuilder<MedicationAlarmWorker>(15, TimeUnit.MINUTES)
            .setInputData(data)
            .build()

        workManager.enqueueUniquePeriodicWork(
            "MedicationAlarmWorker",
            ExistingPeriodicWorkPolicy.UPDATE,
            workRequest
        )
    }
}
