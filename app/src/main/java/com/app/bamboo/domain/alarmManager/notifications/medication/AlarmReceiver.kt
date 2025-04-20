package com.app.bamboo.domain.alarmManager.notifications.medication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.app.bamboo.domain.alarmManager.notifications.ShowNotification

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            val id = intent?.getLongExtra("id", -1) ?: -1
            Log.d("ESSE É O ID", "ID: $id")
            val medicationName = intent?.getStringExtra("medication_name") ?: "Sem nome"
            ShowNotification.showMedicationNotification(it, id, medicationName)
        }
    }
}