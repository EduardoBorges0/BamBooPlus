package com.app.bamboo.presentation.viewModel.medications

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.bamboo.data.models.medications.MedicationEntities
import com.app.bamboo.data.models.medications.MedicationSchedule
import com.app.bamboo.domain.repositories.medications.MedicationRepository
import com.app.bamboo.domain.repositories.medications.MedicationScheduleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicationDetailsViewModel @Inject constructor(
    private val repository: MedicationRepository,
    private val scheduleRepository: MedicationScheduleRepository
) : ViewModel() {
    private val _everyMedications = MutableStateFlow<List<MedicationEntities>>(emptyList())
    val everyMedications: StateFlow<List<MedicationEntities>> = _everyMedications

    private val _everyMedicationsTime = MutableStateFlow<List<MedicationSchedule>>(emptyList())
    val everyMedicationsTime: StateFlow<List<MedicationSchedule>> = _everyMedicationsTime

    fun getMedicationsById(id: Long) {
        viewModelScope.launch {
            _everyMedications.value = repository.getMedicationsById(id)
        }
    }

    fun getMedicationsTime(id: Long) {
        viewModelScope.launch {
            _everyMedicationsTime.value =
                scheduleRepository.getAllMedicationsScheduleById(id).first()
        }
    }

    fun updateMedications(
        id: Long,
        medicationName: String,
        description: String,
        pillOrDrop: String,
        daysOrHour: String,
        medicationTime: String,
        date: String,
        quantity: Int,
        time: Long,
        quantityThreshold: Int,
        amountMedication: Int
    ) {
        viewModelScope.launch {
            repository.updateMedication(
                id,
                medicationName,
                description,
                pillOrDrop,
                daysOrHour,
                medicationTime,
                date,
                quantity,
                time,
                quantityThreshold,
                amountMedication
            )
        }
    }
    fun deleteMedications(id: Long) {
        viewModelScope.launch {
            repository.deleteMedication(id = id)
        }
    }
}