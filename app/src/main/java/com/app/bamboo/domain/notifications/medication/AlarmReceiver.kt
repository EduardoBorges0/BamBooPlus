package com.app.bamboo.domain.notifications.medication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.app.bamboo.domain.notifications.ShowNotification

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            val id = intent?.getLongExtra("medication_id", -1) ?: -1
            val medicationName = intent?.getStringExtra("medication_name") ?: "Sem nome"
            ShowNotification.showMedicationNotification(it, id, medicationName)
        }
    }
}