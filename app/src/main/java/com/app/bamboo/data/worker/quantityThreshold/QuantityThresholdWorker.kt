package com.app.bamboo.data.worker.quantityThreshold

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.app.bamboo.domain.alarmManager.notifications.ShowNotification
import com.app.bamboo.domain.repositories.medications.MedicationRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class QuantityThresholdWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val medicationRepository: MedicationRepository
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        medicationRepository.getIdIfQuantityLower().forEach {
            ShowNotification.showMedicationStock(
                applicationContext,
                it.id,
                it.medicationName
            )
            Log.d("ALARM WORKER", "Alarm chamando oooo")
        }
        return Result.success()
    }
}