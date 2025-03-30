package com.app.bamboo.domain.usecases.workers

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.app.bamboo.presentation.notifications.scheduleNotification
import java.time.Duration
import java.time.LocalTime

class MedicationAlarmWorker(
    context: Context,
    workerParams: WorkerParameters,
) : Worker(context, workerParams) {
    override fun doWork(): Result {
        val medications = inputData.getStringArray("medications")?.toList() ?: emptyList()
        val context = applicationContext
        val now = LocalTime.now()

        val parsedTimes = medications.mapNotNull { time ->
            runCatching { LocalTime.parse(time) }.getOrNull()
        }

        val futureTimes = parsedTimes.filter { it.isAfter(now) || it == now }
        val sortedTimes = if (futureTimes.isNotEmpty()) {
            futureTimes.sortedBy { Duration.between(now, it).seconds }
        } else {
            parsedTimes.map { it.plusHours(24) }.sortedBy { it.toSecondOfDay() }
        }

        Log.d("MedicationWorker", "Medicações agendadas: $sortedTimes")

        sortedTimes.forEach { time ->
            scheduleNotification(context, time.hour, time.minute)
        }

        Log.d("MedicationWorker", "Worker executado! Hora atual: ${LocalTime.now()}")
        Log.d("MedicationWorker", "Medicações agendadas: ${medications}")

        return Result.success()
    }
}
