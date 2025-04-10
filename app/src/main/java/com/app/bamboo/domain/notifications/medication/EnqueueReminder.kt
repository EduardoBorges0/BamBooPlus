package com.app.bamboo.domain.notifications.medication

import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.app.bamboo.data.worker.MedicationAlarmWorker
import javax.inject.Inject

class EnqueueReminder @Inject constructor(
    private val workManager: WorkManager,
) {
    operator fun invoke(
        medications: List<String>?,
        id: List<Long>?,
        medicationName: List<String>?
    ) {
        val inputData = workDataOf(
            "medications" to medications?.toTypedArray(),
            "id" to id?.map { it.toString() }?.toTypedArray(),
            "medication_name" to medicationName?.map { it }?.toTypedArray()
        )

        val workRequest = OneTimeWorkRequestBuilder<MedicationAlarmWorker>()
            .setInputData(inputData)
            .build()

        workManager.enqueue(workRequest)
    }
}
