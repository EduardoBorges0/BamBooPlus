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
import com.app.bamboo.domain.notifications.ScheduleNextNotificationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotifyViewModel @Inject constructor(
    private val scheduleNextNotification: ScheduleNextNotificationUseCase,
    private val medicationScheduleRepository: MedicationScheduleRepository,
) : ViewModel() {
    val medicationSchedules: LiveData<List<String>> =
        medicationScheduleRepository.getAllSchedules()

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
            Log.d("MEDICATION LIST", "MEDICATION ${medicationSchedules.value}")
            scheduleNextNotification.invoke(medicationSchedules.value)
        }
    }
}