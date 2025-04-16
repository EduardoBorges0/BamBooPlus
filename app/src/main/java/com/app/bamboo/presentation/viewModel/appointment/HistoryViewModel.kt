package com.app.bamboo.presentation.viewModel.appointment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.app.bamboo.R
import com.app.bamboo.data.models.appointments.AppointmentEntities
import com.app.bamboo.domain.repositories.appointments.AppointmentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class HistoryViewModel @Inject constructor(private val repository: AppointmentRepository) :
    ViewModel() {
    private val _isAccomplish = MutableLiveData<List<AppointmentEntities>>()
    val isAccomplish: LiveData<List<AppointmentEntities>> = _isAccomplish

    private val _isNullOrEmpty = MutableLiveData<Boolean>()
    val isNullOrEmpty: LiveData<Boolean> = _isNullOrEmpty

    fun appointmentHistory() {
        viewModelScope.launch {
            val filterIsAccomplish = repository.getAllAppointment().value?.filter {
                it.accomplish == false || it.accomplish == true
            }
            if(filterIsAccomplish != null && filterIsAccomplish.isNotEmpty()){
                _isAccomplish.value = filterIsAccomplish
            }else{
                _isNullOrEmpty.value = true
            }
        }
    }
}