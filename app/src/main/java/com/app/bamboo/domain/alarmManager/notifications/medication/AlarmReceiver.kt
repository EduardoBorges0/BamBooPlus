package com.app.bamboo.domain.alarmManager.notifications.medication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.app.bamboo.data.models.medications.MedicationSchedule
import com.app.bamboo.domain.alarmManager.notifications.ShowNotification
import com.app.bamboo.domain.repositories.medications.MedicationScheduleRepository
import com.app.bamboo.utils.TimeUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var repository: MedicationScheduleRepository

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let { ctx ->
            val getAllMedications = repository.getAllMedicationSchedules()
            when (intent?.action) {
                Intent.ACTION_BOOT_COMPLETED, Intent.ACTION_MY_PACKAGE_REPLACED -> {
                    Log.d(
                        "AlarmReceiver",
                        "Dispositivo reiniciado ou app atualizado. Reagendando alarmes..."
                    )
                    reScheduleAllAlarms(ctx, getAllMedications)
                }

                else -> {
                    val id = intent?.getLongExtra("id", -1) ?: -1
                    val medicationName = intent?.getStringExtra("medication_name") ?: "Sem nome"
                    ShowNotification.showMedicationNotification(ctx, id, medicationName)
                }
            }
        }
    }

    private fun reScheduleAllAlarms(
        context: Context,
        getAllMedications: Flow<List<MedicationSchedule>>
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            getAllMedications.collect {
                it.map {
                    val formatter = TimeUtils.formattedLocalDateTime(it.scheduledTime)
                    val date = TimeUtils.formattedLocalDate(it.date)
                    scheduleNotification(
                        scheduleRepository = repository,
                        context = context,
                        hourOrDay = it.hoursOrDays,
                        interval = it.intervalTime.toInt(),
                        hour = formatter.hour.toInt(),
                        minute = formatter.minute.toInt(),
                        day = date.dayOfMonth.toInt(),
                        month = date.monthValue.toInt(),
                        year = date.year.toInt(),
                        medicationName = it.medicationName,
                        date = date,
                        id = it.id.toString()
                    )
                }
            }
        }
        Log.d("AlarmReceiver", "Função de reagendamento chamada (implementar)")
    }
}
