package com.app.bamboo.utils.notifications

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import java.util.Calendar

@SuppressLint("ScheduleExactAlarm")
fun scheduleNotification(
    context: Context,
    hourNow: Int,
    minuteNow: Int,
    title: String,
    message: String,
) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, NotificationReceiver::class.java).apply {
        putExtra("NOTIFICATION_TITLE", title)
        putExtra("NOTIFICATION_MESSAGE", message)
    }
    val pendingIntent = PendingIntent.getBroadcast(
        context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val now = Calendar.getInstance()
    val calendar = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, hourNow)
        set(Calendar.MINUTE, minuteNow)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)

        if (timeInMillis <= now.timeInMillis) {
            add(Calendar.DAY_OF_MONTH, 1)
        }
    }

    Log.d("AlarmManager", "Alarme ajustado para: ${calendar.time}")

    alarmManager.setAlarmClock(
        AlarmManager.AlarmClockInfo(calendar.timeInMillis, pendingIntent),
        pendingIntent
    )
}
