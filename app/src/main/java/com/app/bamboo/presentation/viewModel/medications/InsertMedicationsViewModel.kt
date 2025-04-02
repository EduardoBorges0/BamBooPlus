package com.app.bamboo.presentation.viewModel.medications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.bamboo.data.models.MedicationSchedule
import com.app.bamboo.domain.repositories.MedicationRepository
import com.app.bamboo.domain.repositories.MedicationScheduleRepository
import com.app.bamboo.utils.TimeUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class InsertMedicationsViewModel @Inject constructor(
    private val repository: MedicationRepository,
    private val medicationScheduleRepository: MedicationScheduleRepository,
): ViewModel() {
    fun insertMedication(
        medicationName: String,
        description: String,
        pillOrDrop: String,
        daysOrHour: String,
        medicationTime: String,
        time: Long,
    ) {
        viewModelScope.launch {
            val medicationId = repository.insertMedication(
                medicationName = medicationName,
                description = description,
                pillOrDrop = pillOrDrop,
                daysOrHour = daysOrHour,
                medicationTime = medicationTime,
                time = time
            )
            insertSchedules(medicationId, medicationTime, time.toInt())
        }
    }
    private suspend fun insertSchedules(medicationId: Long, startTime: String, intervalHours: Int) {
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        var nextTime = TimeUtils.formattedLocalDate(startTime)

        val schedules = mutableListOf<MedicationSchedule>()

        repeat(24 / intervalHours) {
            schedules.add(
                MedicationSchedule(
                    medicationId = medicationId,
                    scheduledTime = nextTime.format(formatter)
                )
            )
            nextTime = nextTime.plusHours(intervalHours.toLong())
        }
        if (schedules.isNotEmpty()) {
            medicationScheduleRepository.insertSchedule(schedules)
        }
    }
}