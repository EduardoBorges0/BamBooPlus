package com.app.bamboo.presentation.viewModel.medications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.bamboo.data.models.MedicationEntities
import com.app.bamboo.data.models.MedicationSchedule
import com.app.bamboo.domain.repositories.MedicationRepository
import com.app.bamboo.domain.repositories.MedicationScheduleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class NotifyMedicationsViewModel @Inject constructor(
    private val scheduleRepository: MedicationScheduleRepository,
    private val medicationRepository: MedicationRepository
) : ViewModel() {
    val medicationTimes: LiveData<List<MedicationSchedule>> = scheduleRepository.getAllMedicationSchedules()

    private val _medication = MutableLiveData<List<MedicationEntities>>()
    val medication: LiveData<List<MedicationEntities>> = _medication

    private val _time = MutableLiveData<String>()
    val time: LiveData<String> = _time

    init {
        medicationTimes.observeForever { list ->
            list?.let {
                getLastTime(it)
            }
        }
    }

    fun getLastTime(medications: List<MedicationSchedule>) {
        viewModelScope.launch {
            val now = LocalTime.now()
            val lastMedication = medications
                .filter { LocalTime.parse(it.scheduledTime).isBefore(now) }
                .maxByOrNull { LocalTime.parse(it.scheduledTime) }
            _time.value = lastMedication?.scheduledTime
        }
    }

    fun getMedicationById(id: Long){
        viewModelScope.launch {
            _medication.value = medicationRepository.getMedicationsById(id)
        }
    }

    fun updateAccomplish(id: Long, accomplish: Boolean){
        viewModelScope.launch {
            medicationRepository.updateAccomplish(id, accomplish)
        }
    }
}
