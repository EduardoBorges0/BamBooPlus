package com.app.bamboo.data.worker.deleteMedicationHistory

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.app.bamboo.domain.repositories.medications.MedicationHistoryRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.time.LocalDate

@HiltWorker
class WeeklyCleanupWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val medicationHistoryRepository: MedicationHistoryRepository
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        Log.d("RODANDO", "AQUI É O WORKER")
        if (LocalDate.now().dayOfWeek.value == 7) {
            Log.d("RODANDO", "AQUI É O WORKER NOS DOMINGOS" )
            medicationHistoryRepository.deleteAllMedicationHistory()
        }
        return Result.success()
    }

}