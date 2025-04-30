package com.app.bamboo.presentation.viewModel.appointment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.bamboo.data.models.appointments.AppointmentEntities
import com.app.bamboo.domain.repositories.appointments.AppointmentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppointmentsViewModel @Inject constructor(private val appointmentsRepository: AppointmentRepository) :
    ViewModel() {
    val getAllApointments: Flow<List<AppointmentEntities>> =
        appointmentsRepository.getAllAppointment()

    private val _appointmentsList = MutableStateFlow<List<AppointmentEntities>>(emptyList())
    val appointmentsList: StateFlow<List<AppointmentEntities>> = _appointmentsList

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    fun getAppointmentsById() {
        viewModelScope.launch {
            appointmentsRepository.getAllAppointment().first().map {
                _appointmentsList.value = appointmentsRepository.getAppointmentsById(it.id.toLong()).first()
            }
        }
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val searchResults: StateFlow<List<AppointmentEntities>> = searchQuery
        .debounce(300)
        .flatMapLatest { query ->
            searchMedication(query)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun onSearchQueryChanged(newQuery: String) {
        _searchQuery.value = newQuery
    }

    private fun searchMedication(query: String): Flow<List<AppointmentEntities>> {
        return getAllApointments.map { list ->
            list.filter {
                it.appointmentType.contains(query, ignoreCase = true)
            }
        }
    }
}