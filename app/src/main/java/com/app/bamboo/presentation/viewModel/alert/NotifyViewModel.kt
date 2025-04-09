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
import com.app.bamboo.data.models.AppointmentSummary
import com.app.bamboo.domain.notifications.appointment.scheduleAppointment
import com.app.bamboo.domain.repositories.MedicationScheduleRepository
import com.app.bamboo.domain.notifications.medication.EnqueueReminder
import com.app.bamboo.domain.repositories.AppointmentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class NotifyViewModel @Inject constructor(
    private val scheduleNextNotification: EnqueueReminder,
    private val medicationScheduleRepository: MedicationScheduleRepository,
    private val appointmentRepository: AppointmentRepository,
) : ViewModel() {
    val timeSchedules: LiveData<List<String>> =
        medicationScheduleRepository.getAllSchedules()

    val medicationId: LiveData<List<Long>> =
        medicationScheduleRepository.getAllMedicationId()

    val medicationName: LiveData<List<String>> = medicationScheduleRepository.getMedicationsName()

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
            scheduleNextNotification.invoke(
                timeSchedules.value,
                medicationId.value,
                medicationName.value
            )
        }
    }

    fun showAppointmentNotification(context: Context) {
        viewModelScope.launch {
            appointments.value?.forEach {
                val appointmentDate = it.appointmentDate
                val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val date = format.parse(appointmentDate)
                val (hour, minute) = it.appointmentTime.split(":").map { it.toInt() }

                date?.let {
                    val calendar = Calendar.getInstance()
                    calendar.time = it
                    val day = calendar.get(Calendar.DAY_OF_MONTH)
                    val month =
                        if (calendar.get(Calendar.MONTH) != 0) calendar.get(Calendar.MONTH) else calendar.get(
                            Calendar.MONTH
                        ) + 1

                    scheduleAppointment(context, day, month, hour, minute, "", "1")
                }
            }
        }
    }
}