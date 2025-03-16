package com.app.bamboo.presentation.viewModel.appointment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.app.bamboo.domain.repositories.AppointmentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class HistoryViewModel @Inject constructor(private val repository: AppointmentRepository) :
    ViewModel() {
    private val _isAccomplish = MutableStateFlow(false)
    val isAccomplish: StateFlow<Boolean> = _isAccomplish.asStateFlow()

    fun appointmentHistory() {
        viewModelScope.launch {
            repository.getAllAppointment()
                .asFlow()
                .map { appointments -> appointments.firstOrNull()?.accomplish ?: false }
                .collect { accomplish ->
                    _isAccomplish.value = accomplish
                }
        }
    }
}