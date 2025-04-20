package com.app.bamboo.domain.alarmManager.notifications.medication

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.app.bamboo.domain.repositories.medications.MedicationRepository
import java.time.LocalDate
import java.util.Calendar

suspend fun scheduleNotification(
    medicationRepository: MedicationRepository,
    context: Context,
    hourOrDay: String,
    date: LocalDate,
    interval: Int,
    hour: Int,
    minute: Int,
    day: Int,
    year: Int,
    month: Int,
    medicationName: String,
    id: String
) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val requestCode = "${hour}_${minute}_$id".hashCode()

    val calendar = Calendar.getInstance().apply {
        if (hourOrDay == "Days") {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            set(Calendar.DAY_OF_MONTH, day)
            set(Calendar.MONTH, month - 1)
            set(Calendar.YEAR, year)

            val currentTime = System.currentTimeMillis()

            while (timeInMillis <= currentTime) {
                add(Calendar.DAY_OF_MONTH, interval)
                medicationRepository.updateDate(
                    date = date.plusDays(interval.toLong()).toString(),
                    id.toLong()
                )
            }
        } else {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            if (timeInMillis <= System.currentTimeMillis()) {
                add(Calendar.DAY_OF_MONTH, 1)
            }
        }

    }
    val intent = Intent(context, AlarmReceiver::class.java).apply {
        putExtra("id", id.toLong())
        putExtra("medication_name", medicationName)
    }
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        requestCode,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )
    Log.d("ESSE É O ID", "ID A: $id")

    alarmManager.setAlarmClock(
        AlarmManager.AlarmClockInfo(calendar.timeInMillis, pendingIntent),
        pendingIntent
    )
}
