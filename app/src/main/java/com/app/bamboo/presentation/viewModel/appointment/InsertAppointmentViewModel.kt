package com.app.bamboo.presentation.viewModel.appointment

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.bamboo.domain.repositories.AppointmentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InsertAppointmentViewModel @Inject constructor(private val repository: AppointmentRepository) :
    ViewModel() {
    fun insertAppointment(
        appointmentType: String,
        onlineOrOnSite: String,
        doctorName: String,
        hospitalName: String,
        appointmentDate: String,
        appointmentTime: String
    ) {
        viewModelScope.launch {
            repository.insertAppointment(
                appointmentType = appointmentType,
                onlineOrOnSite = onlineOrOnSite,
                doctorName = doctorName,
                hospitalName = hospitalName,
                appointmentDate = appointmentDate,
                appointmentTime = appointmentTime
            )
        }
    }
}