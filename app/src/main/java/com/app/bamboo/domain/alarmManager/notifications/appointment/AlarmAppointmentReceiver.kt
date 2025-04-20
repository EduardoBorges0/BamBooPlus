package com.app.bamboo.domain.alarmManager.notifications.appointment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.app.bamboo.domain.alarmManager.notifications.ShowNotification

class AlarmAppointmentReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            val appointmentType = intent?.getStringExtra("appointmentName")
            val time = intent?.getStringExtra("time")
            val id = intent?.getStringExtra("id")?.toLong()
            ShowNotification.showAppointmentNotification(
                it, id, appointmentType.toString(),
                time.toString()
            )
        }
    }
}