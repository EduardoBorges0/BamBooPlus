package com.app.bamboo.presentation.viewModel.alert

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.bamboo.service.ScheduleAlarmService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotifyViewModel @Inject constructor(
    private val scheduleAlarmService: ScheduleAlarmService,
) : ViewModel() {
    val getBiggerToLower: StateFlow<List<String>> = scheduleAlarmService.medicationsTime

    init {
        updateMedicationList()
        startAutoUpdate()
    }

    private fun updateMedicationList() {
        viewModelScope.launch {
            val sortedList = scheduleAlarmService.getSortedMedications()
            scheduleAlarmService.updateMedicationList(sortedList)
        }
    }

    private fun moveFirstToLastIfNeeded() {
        viewModelScope.launch {
            scheduleAlarmService.moveFirstToLastIfNeeded()
        }
    }

    private fun startAutoUpdate() {
        viewModelScope.launch {
            while (isActive) {
                delay(6000)
                moveFirstToLastIfNeeded()
            }
        }
    }

    fun showNotifications(activity: Activity, context: Context) {
        viewModelScope.launch {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        activity,
                        arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                        101
                    )
                }
            }
            val list = getBiggerToLower.value
            if (list.isNotEmpty()) {
                scheduleAlarmService.scheduleNextNotification(context, list)
            }
        }
    }
}
