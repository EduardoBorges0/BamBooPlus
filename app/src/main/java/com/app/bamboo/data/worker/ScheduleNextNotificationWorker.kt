package com.app.bamboo.data.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.app.bamboo.domain.notifications.medication.scheduleNotification
import java.time.Duration
import java.time.LocalTime

class MedicationAlarmWorker(
    context: Context,
    workerParams: WorkerParameters,
) : Worker(context, workerParams) {
    override fun doWork(): Result {
        val timeSchedules = inputData.getStringArray("medications")?.toList() ?: emptyList()
        val medicationId = inputData.getStringArray("medication_id")?.toList() ?: emptyList()
        val medicationName = inputData.getStringArray("medication_name")?.toList() ?: emptyList()

        sortedMedication(timeSchedules, medicationId, medicationName)
        return Result.success()
    }

    private fun sortedMedication(
        medications: List<String>,
        ids: List<String>,
        medicationNames: List<String>?,
    ) {
        val context = applicationContext
        val now = LocalTime.now()

        val nameList = medicationNames ?: List(medications.size) { "Desconhecido" }

        val parsedTriples = medications.indices.mapNotNull { i ->
            val timeStr = medications.getOrNull(i)
            val id = ids.getOrNull(i)
            val name = nameList.getOrNull(i)

            val time = runCatching { LocalTime.parse(timeStr) }.getOrNull()
            if (time != null && id != null && name != null) {
                Triple(time, id, name)
            } else null
        }

        val futureTriples = parsedTriples.filter { (time, _, _) ->
            time.isAfter(now) || time == now
        }

        val sortedTriples = if (futureTriples.isNotEmpty()) {
            futureTriples.sortedBy { (time, _, _) -> Duration.between(now, time).seconds }
        } else {
            parsedTriples.map { (time, id, name) -> Triple(time.plusHours(24), id, name) }
                .sortedBy { (time, _, _) -> time.toSecondOfDay() }
        }

        sortedTriples.forEach { (time, id, name) ->
            scheduleNotification(context, time.hour, time.minute, id = id, medicationName = name)
        }
    }

}
