package com.app.bamboo.presentation.viewModel.appointment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.bamboo.data.models.AppointmentEntities
import com.app.bamboo.data.models.AppointmentSummary
import com.app.bamboo.domain.repositories.AppointmentRepository
import com.app.bamboo.utils.TimeUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
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
                val appointmentTime = TimeUtils.formattedLocalTime(it.appointmentTime)
                appointmentDate.isBefore(today) || appointmentDate.isEqual(today) && appointmentTime.isBefore(
                    now
                )
            }
            if (filteredAppointments != null) {
                if (filteredAppointments.isNotEmpty()) {
                    repository.updateAccomplish(filteredAppointments[0].id, false)
                }
            }
        }
    }
}