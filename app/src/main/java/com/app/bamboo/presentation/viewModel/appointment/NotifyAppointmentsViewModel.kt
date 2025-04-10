package com.app.bamboo.presentation.viewModel.appointment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.bamboo.data.models.AppointmentEntities
import com.app.bamboo.domain.repositories.AppointmentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotifyAppointmentsViewModel @Inject constructor(private val repository: AppointmentRepository) :
    ViewModel() {
        private val _appointmentsList = MutableStateFlow<List<AppointmentEntities>>(emptyList())
        val appointmentsList: StateFlow<List<AppointmentEntities>> = _appointmentsList

    fun getAppointmentsById(id: Long){
        viewModelScope.launch {
            _appointmentsList.value = repository.getAppointmentsById(id)
        }
    }
}