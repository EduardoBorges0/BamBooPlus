package com.app.bamboo.domain.notifications

import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.app.bamboo.data.worker.MedicationAlarmWorker
import javax.inject.Inject

class ScheduleNextNotificationUseCase @Inject constructor(
    private val workManager: WorkManager,
) {
    operator fun invoke(
        medications: List<String>?,
    ) {
        val data = workDataOf("medications" to medications?.toTypedArray())

        val workRequest = OneTimeWorkRequestBuilder<MedicationAlarmWorker>()
            .setInputData(data)
            .build()
        workManager.enqueue(workRequest)
    }
}
