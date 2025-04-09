package com.app.bamboo.domain.notifications.appointment

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun scheduleAppointment(
    context: Context,
    DayOfMonth: Int,
    Month: Int,
    hour: Int,
    minute: Int,
    medicationName: String,
    id: String
) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val requestCode = "${DayOfMonth}_${Month}_$id".hashCode()

    val calendar = Calendar.getInstance().apply {
        set(Calendar.DAY_OF_MONTH, DayOfMonth)
        set(Calendar.MONTH, Month)
        set(Calendar.HOUR_OF_DAY, hour)
        set(Calendar.MINUTE, minute)
    }

    val intent = Intent(context, AlarmAppointmentReceiver::class.java)

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
    val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    val formattedTime = sdf.format(calendar.time)
    Log.d("ScheduleAlarm", "Alarme agendado para: $formattedTime (medicação: $medicationName, id: $id)")
}
