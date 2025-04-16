package com.app.bamboo.presentation.viewModel.appointment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.bamboo.data.models.appointments.AppointmentEntities
import com.app.bamboo.data.models.appointments.AppointmentSummary
import com.app.bamboo.domain.repositories.appointments.AppointmentRepository
import com.app.bamboo.utils.TimeUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.chrono.ChronoLocalDateTime
import javax.inject.Inject

@HiltViewModel
class NotifyAppointmentsViewModel @Inject constructor(private val repository: AppointmentRepository) :
    ViewModel() {
    private val _appointmentsList = MutableStateFlow<List<AppointmentEntities>>(emptyList())
    val appointmentsList: StateFlow<List<AppointmentEntities>> = _appointmentsList

    val appointmentsListAccomplishNull: LiveData<List<AppointmentSummary>>? =
        repository.getAppointmentSummaries()

    fun getAppointmentsById(id: Long) {
        viewModelScope.launch {
            _appointmentsList.value = repository.getAppointmentsById(id)
        }
    }

    fun updateAccomplishAppointmentIsNull() {
        viewModelScope.launch {
            val today = LocalDate.now()
            val now = LocalTime.now()
            val filteredAppointments = appointmentsListAccomplishNull?.value?.filter {
                val appointmentDate = TimeUtils.formattedLocalDate(it.appointmentDate)
                val appointmentTime = TimeUtils.formattedLocalDateTime(it.appointmentTime)
                appointmentDate.isBefore(today) || appointmentDate.isEqual(today) && appointmentTime.isBefore(
                    now
                )
            }
            if (filteredAppointments != null) {
                if (filteredAppointments.isNotEmpty()) {
                    filteredAppointments.forEach {
                        repository.updateAccomplish(it.id, false)
                    }                }
            }
        }
    }
}