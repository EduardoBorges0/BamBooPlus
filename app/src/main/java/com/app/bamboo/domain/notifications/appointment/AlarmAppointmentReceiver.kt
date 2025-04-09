package com.app.bamboo.domain.notifications.appointment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.app.bamboo.domain.notifications.ShowNotification

class AlarmAppointmentReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            val appointmentType = intent?.getStringExtra("appointmentName")
            val id = intent?.getStringExtra("id")?.toLong()
            ShowNotification.showAppointmentNotification(it, id, appointmentType.toString())
        }
    }
}