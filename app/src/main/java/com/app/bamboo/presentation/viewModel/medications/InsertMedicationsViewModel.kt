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
) : ViewModel() {
    fun insertMedication(
        medicationName: String,
        description: String,
        pillOrDrop: String,
        daysOrHour: String,
        medicationTime: String,
        time: Long,
    ) {
        viewModelScope.launch {
            if(daysOrHour == "Days"){
                val day = time * 24
                val medicationId = repository.insertMedication(
                    medicationName = medicationName,
                    description = description,
                    pillOrDrop = pillOrDrop,
                    daysOrHour = daysOrHour,
                    medicationTime = medicationTime,
                    time = day
                )
                insertSchedules(medicationId, daysOrHour, medicationTime, day.toInt(), medicationName)
            }else{
                val medicationId = repository.insertMedication(
                    medicationName = medicationName,
                    description = description,
                    pillOrDrop = pillOrDrop,
                    daysOrHour = daysOrHour,
                    medicationTime = medicationTime,
                    time = time
                )
                insertSchedules(medicationId, daysOrHour, medicationTime, time.toInt(), medicationName)
            }
        }
    }

    suspend fun insertSchedules(
        medicationId: Long,
        daysOrHour: String,
        startTime: String,
        intervalHours: Int,
        medicationName: String,
    ): List<MedicationSchedule> {
        var nextTime = TimeUtils.formattedLocalTime(startTime)

        val schedules = mutableListOf<MedicationSchedule>()
        val intervalDays = intervalHours * 24
        repeat(if(daysOrHour == "Hours") 24 / intervalHours else intervalDays / 24) {
            schedules.add(
                MedicationSchedule(
                    medicationId = medicationId,
                    medicationName = medicationName,
                    scheduledTime = nextTime.toString()
                )
            )
            nextTime = if (daysOrHour == "Hours") {
                nextTime.plusHours(intervalHours.toLong())
            } else {
                nextTime.plusHours(intervalDays.toLong())  // Incrementa 1 dia para "Days"
            }        }
        if (schedules.isNotEmpty()) {
            medicationScheduleRepository.insertSchedule(schedules)
        }
        return schedules
    }
}