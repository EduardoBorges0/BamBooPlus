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
        CoroutineScope(Dispatchers.IO).launch {
            val onlyTrue = repository.getAllMedicationSchedules().first().filter {
                it.accomplish == true
            }
            onlyTrue.map {
                repository.updateAccomplishSchedule(it.id, false)
            }
            Log.d("REPO", "REPO : $onlyTrue")
        }
    }
}
