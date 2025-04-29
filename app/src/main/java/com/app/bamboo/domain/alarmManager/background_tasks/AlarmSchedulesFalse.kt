package com.app.bamboo.domain.alarmManager.background_tasks

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.app.bamboo.domain.repositories.medications.MedicationScheduleRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AlarmSchedulesFalse : BroadcastReceiver() {

    @Inject
    lateinit var repository: MedicationScheduleRepository

    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            Intent.ACTION_BOOT_COMPLETED, Intent.ACTION_MY_PACKAGE_REPLACED -> {
                everyScheduleFalse(context)
            }
            else -> scheduleFalse()
        }
    }

    private fun scheduleFalse() {
        CoroutineScope(Dispatchers.IO).launch {
            val onlyTrue = repository.getAllMedicationSchedules().first().filter {
                it.accomplish == true
            }
            Log.d("AlarmSchedulesFalse", "Itens com accomplish == true: $onlyTrue")

            onlyTrue.map {
                Log.d("AlarmSchedulesFalse", "Atualizando o id ${it.id} para accomplish = false")
                repository.updateAccomplishSchedule(it.id, false)
            }
        }
    }
}

