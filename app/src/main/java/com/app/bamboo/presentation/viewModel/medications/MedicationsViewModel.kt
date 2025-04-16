package com.app.bamboo.presentation.viewModel.medications

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.bamboo.data.models.medications.MedicationEntities
import com.app.bamboo.data.models.medications.MedicationSchedule
import com.app.bamboo.domain.repositories.medications.MedicationRepository
import com.app.bamboo.domain.repositories.medications.MedicationScheduleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import java.lang.Thread.State
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class MedicationsViewModel @Inject constructor(
    private val repository: MedicationRepository,
    private val scheduleRepository: MedicationScheduleRepository
) : ViewModel() {
    private val _getAllMedications = MutableLiveData<List<MedicationEntities>?>(emptyList())
    val getAllMedications: LiveData<List<MedicationEntities>?> = _getAllMedications

    private val _getNextMedication = MutableLiveData<MedicationSchedule>()
    val getNextMedication: LiveData<MedicationSchedule> = _getNextMedication

    internal val medicationTimes = MutableLiveData<List<MedicationSchedule>>()


    fun getAllMedications() {
        viewModelScope.launch {
            if (repository.getAllMedications().value != null) {
                _getAllMedications.value = repository.getAllMedications().value
            }
        }
    }


    fun updateNextMedication(now: LocalTime = LocalTime.now()) {
        viewModelScope.launch {
            val nextMedication = medicationTimes.value?.filter {
                LocalTime.parse(it.scheduledTime).isAfter(now)
            }?.minByOrNull { LocalTime.parse(it.scheduledTime) }

            if (nextMedication != null) {
                _getNextMedication.value = nextMedication
            }
        }
    }


    suspend fun searchMedication() {

    }

    fun deleteMedications(id: Long) {
        viewModelScope.launch {
            repository.deleteMedication(id = id)
        }
    }
}