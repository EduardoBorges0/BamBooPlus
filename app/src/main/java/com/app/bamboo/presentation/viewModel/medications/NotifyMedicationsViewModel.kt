package com.app.bamboo.presentation.viewModel.medications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.bamboo.data.models.medications.MedicationSchedule
import com.app.bamboo.domain.repositories.medications.MedicationHistoryRepository
import com.app.bamboo.domain.repositories.medications.MedicationRepository
import com.app.bamboo.domain.repositories.medications.MedicationScheduleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class NotifyMedicationsViewModel @Inject constructor(
    private val scheduleRepository: MedicationScheduleRepository,
    private val medicationRepository: MedicationRepository,
    private val medicationHistoryRepository: MedicationHistoryRepository
) : ViewModel() {
    val medicationTimes: Flow<List<MedicationSchedule>> =
        scheduleRepository.getAllMedicationSchedules()

    private val _medication = MutableLiveData<List<MedicationSchedule>>()
    val medication: LiveData<List<MedicationSchedule>> = _medication

    private val _time = MutableLiveData<String>()
    val time: LiveData<String> = _time

    init {
        viewModelScope.launch {
            medicationTimes.collect { list ->
                list.let {
                    getLastTime(it)
                }
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

    fun updateQuantity(id: Long){
        viewModelScope.launch {
            medicationRepository.getMedicationsById(id).map {
                medicationRepository.updateQuantity(it.quantity.minus(1), id)
            }
        }
    }

    fun getMedicationById(id: Long) {
        viewModelScope.launch {
            _medication.value = scheduleRepository.getSchedulesMedicationsById(id)
        }
    }

    fun updateAccomplishSchedule(id: Long, accomplish: Boolean) {
        viewModelScope.launch {
            scheduleRepository.updateAccomplishSchedule(id, accomplish)
        }
    }

    fun insertMedicationHistory(
        medicationId: Long,
        medicationName: String,
        medicationTime: String,
        dayOfWeek: String
    ) {
        viewModelScope.launch {
            medicationHistoryRepository.insertMedication(
                medicationId = medicationId,
                medicationName = medicationName,
                medicationTime = medicationTime,
                dayOfWeek = dayOfWeek
            )
        }
    }
}
