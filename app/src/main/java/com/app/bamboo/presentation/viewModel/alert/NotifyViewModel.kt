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
import com.app.bamboo.domain.repositories.MedicationScheduleRepository
import com.app.bamboo.domain.notifications.medication.EnqueueReminder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotifyViewModel @Inject constructor(
    private val scheduleNextNotification: EnqueueReminder,
    private val medicationScheduleRepository: MedicationScheduleRepository,
) : ViewModel() {
    val timeSchedules: LiveData<List<String>> =
        medicationScheduleRepository.getAllSchedules()

    val medicationId: LiveData<List<Long>> =
        medicationScheduleRepository.getAllMedicationId()

    val medicationName: LiveData<List<String>> = medicationScheduleRepository.getMedicationsName()

    fun showNotifications(activity: Activity?, context: Context) {
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
}