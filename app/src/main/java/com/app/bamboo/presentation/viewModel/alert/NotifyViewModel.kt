package com.app.bamboo.presentation.viewModel.alert

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.bamboo.data.models.appointments.AppointmentSummary
import com.app.bamboo.data.models.medications.MedicationSchedule
import com.app.bamboo.domain.alarmManager.notifications.appointment.scheduleAppointment
import com.app.bamboo.domain.repositories.medications.MedicationScheduleRepository
import com.app.bamboo.domain.alarmManager.notifications.medication.scheduleNotification
import com.app.bamboo.domain.repositories.appointments.AppointmentRepository
import com.app.bamboo.domain.repositories.medications.MedicationRepository
import com.app.bamboo.utils.TimeUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class NotifyViewModel @Inject constructor(
    private val medicationScheduleRepository: MedicationScheduleRepository,
    private val medicationRepository: MedicationRepository,
    private val appointmentRepository: AppointmentRepository,
) : ViewModel() {
    val timeSchedules: LiveData<List<String>> =
        medicationScheduleRepository.getAllSchedules()

    val medicationId: LiveData<List<Long>> =
        medicationScheduleRepository.getAllMedicationId()

    val medicationName: LiveData<List<String>> = medicationScheduleRepository.getMedicationsName()

    val getAllMedications: Flow<List<MedicationSchedule>> =
        medicationScheduleRepository.getAllMedicationSchedules()

    val appointments: LiveData<List<AppointmentSummary>> =
        appointmentRepository.getAppointmentSummaries()

    fun showMedicationNotifications(activity: Activity?, context: Context) {
        viewModelScope.launch {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    activity?.let {
                        ActivityCompat.requestPermissions(
                            it,
                            arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                            101
                        )
                    }
                }
            }
            Log.d(
                "ALARME",
                "esta chegando aq ${getAllMedications}"
            )
            getAllMedications.collect {
                it.map {
                val formatter = TimeUtils.formattedLocalDateTime(it.scheduledTime)
                val date = TimeUtils.formattedLocalDate(it.date)
                Log.d(
                    "ALARME",
                    "ESTA É A AGENDA ${formatter.hour}, ${it.medicationId}, ${it.medicationName}"
                )
                scheduleNotification(
                    scheduleRepository = medicationScheduleRepository,
                    context = context,
                    medicationRepository = medicationRepository,
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
    }

    fun showAppointmentNotification(context: Context) {
        viewModelScope.launch {
            appointments.value?.forEach { appointment ->
                val date = TimeUtils.formattedLocalDate(appointment.appointmentDate)
                val (hour, minute) = appointment.appointmentTime.split(":").map { it.toInt() }

                date.let {
                    val calendar = Calendar.getInstance().apply {
                        set(Calendar.HOUR_OF_DAY, hour)
                        set(Calendar.MINUTE, minute)
                        set(Calendar.SECOND, 0)
                        set(Calendar.MILLISECOND, 0)

                        add(Calendar.MINUTE, -30)
                    }

                    val day = calendar.get(Calendar.DAY_OF_MONTH)
                    val month = calendar.get(Calendar.MONTH) + 1

                    scheduleAppointment(
                        context,
                        day,
                        month,
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        appointment.appointmentType,
                        appointment.id.toString()
                    )
                }
            }
        }
    }

}