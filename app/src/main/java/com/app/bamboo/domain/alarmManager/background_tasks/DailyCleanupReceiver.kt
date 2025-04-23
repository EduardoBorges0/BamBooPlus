package com.app.bamboo.domain.alarmManager.background_tasks

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class DailysCleanupReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        Log.d("ALARM", "Alarm triggered!")
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            val repository = MedicationScheduleRepositoryProvider.getRepository(context)
            val ids = repository.getAllId().first()
            ids.forEach {
                repository.updateAccomplishSchedule(it, null)
            }
        }
    }
}
