package com.app.bamboo.presentation.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent


class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            ShowNotification.showMedicationNotification(it)
        }
    }
}