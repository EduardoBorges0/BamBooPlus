package com.app.bamboo.domain.alarmManager.background_tasks

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.Calendar

fun everyScheduleFalse(
    context: Context,
) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val calendar = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
    }

    val intent = Intent(context, AlarmSchedulesFalse::class.java)

    val pendingIntent = PendingIntent.getBroadcast(
        context,
        1,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    alarmManager.setAlarmClock(
        AlarmManager.AlarmClockInfo(calendar.timeInMillis, pendingIntent),
        pendingIntent
    )
}
