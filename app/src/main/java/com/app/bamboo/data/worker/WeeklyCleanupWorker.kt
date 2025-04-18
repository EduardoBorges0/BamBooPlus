package com.app.bamboo.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.app.bamboo.domain.repositories.medications.MedicationHistoryRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@HiltWorker
class WeeklyCleanupWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val medicationHistoryRepository: MedicationHistoryRepository
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {

        val now = LocalDateTime.now()
        val prefs = applicationContext.getSharedPreferences("worker_prefs", Context.MODE_PRIVATE)
        val lastCleanupDate = prefs.getString("last_cleanup", null)

        val formatter = DateTimeFormatter.ISO_LOCAL_DATE
        val todayString = now.toLocalDate().format(formatter)

        val isSundayLate = now.dayOfWeek == DayOfWeek.SUNDAY &&
                now.hour == 23 &&
                now.minute >= 55

        // Já executou essa semana?
        val alreadyCleanedToday = lastCleanupDate == todayString

        if (isSundayLate && !alreadyCleanedToday) {
            medicationHistoryRepository.deleteAllMedicationHistory()

            prefs.edit()
                .putString("last_cleanup", todayString)
                .apply()
        }

        return Result.success()
    }

}
