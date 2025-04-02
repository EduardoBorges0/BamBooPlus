package com.app.bamboo.presentation.viewModel.alert

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.bamboo.domain.repositories.MedicationRepository
import com.app.bamboo.domain.repositories.MedicationScheduleRepository
import com.app.bamboo.service.ScheduleAlarmService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class NotifyViewModel @Inject constructor(
    private val scheduleAlarmService: ScheduleAlarmService,
    private val medicationRepository: MedicationRepository,
    private val medicationScheduleRepository: MedicationScheduleRepository
) : ViewModel() {
    val medicationsTime = MutableStateFlow<List<String>>(emptyList())
    private val _medicationSchedules = MutableStateFlow<List<String>>(emptyList())
    val medicationSchedules: StateFlow<List<String>> = _medicationSchedules

    fun getSchedulesForMedication(medicationId: Long) {
        viewModelScope.launch {
            medicationScheduleRepository.getSchedulesForMedication(medicationId).observeForever { schedules ->
                _medicationSchedules.value = schedules
            }
        }
    }
    init {
        viewModelScope.launch {
            medicationRepository.getMedicationsTime().collect { times ->
                medicationsTime.value = times
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
            Log.d("MEDICATION LIST", "MEDICATION $medicationsTime")
            if (medicationsTime.value.isNotEmpty()) {
                scheduleAlarmService.scheduleNextNotification(medicationsTime.value ?: emptyList())
            }
        }
    }
}