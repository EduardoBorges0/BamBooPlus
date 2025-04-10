package com.app.bamboo.domain.notifications.medication

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.Calendar

fun scheduleNotification(
    context: Context,
    hourNow: Int,
    minuteNow: Int,
    medicationName: String,
    id: String
) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val requestCode = "${hourNow}_${minuteNow}_$id".hashCode()

    val calendar = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, hourNow)
        set(Calendar.MINUTE, minuteNow)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
        if (timeInMillis <= System.currentTimeMillis()) {
            add(Calendar.DAY_OF_MONTH, 1)
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

    alarmManager.setAlarmClock(
        AlarmManager.AlarmClockInfo(calendar.timeInMillis, pendingIntent),
        pendingIntent
    )
}
