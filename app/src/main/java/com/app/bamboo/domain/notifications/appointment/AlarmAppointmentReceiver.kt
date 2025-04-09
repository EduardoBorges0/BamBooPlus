package com.app.bamboo.domain.notifications.appointment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.app.bamboo.domain.notifications.ShowNotification

class AlarmAppointmentReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            ShowNotification.showAppointmentNotification(it)
        }
    }
}