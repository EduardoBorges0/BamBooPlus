package com.app.bamboo.presentation.viewModel.medications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.bamboo.data.models.medications.MedicationSchedule
import com.app.bamboo.domain.repositories.medications.MedicationRepository
import com.app.bamboo.domain.repositories.medications.MedicationScheduleRepository
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
        date: String,
        quantity: Int,
        time: Long,
    ) {
        viewModelScope.launch {
            val medicationId = repository.insertMedication(
                medicationName = medicationName,
                description = description,
                pillOrDrop = pillOrDrop,
                daysOrHour = daysOrHour,
                medicationTime = medicationTime,
                date = date,
                quantity = quantity,
                time = time
            )
            insertSchedules(
                medicationId = medicationId,
                daysOrHour = daysOrHour,
                date = date,
                startTime = medicationTime,
                intervalHours = time.toInt(),
                medicationName = medicationName,
                description = description
            )
        }
    }

    suspend fun insertSchedules(
        medicationId: Long,
        daysOrHour: String,
        date: String,
        startTime: String,
        intervalHours: Int,
        description: String,
        medicationName: String,
    ): List<MedicationSchedule> {
        var nextTime = TimeUtils.formattedLocalDateTime(startTime)
        var nextDate = TimeUtils.formattedLocalDate(date)
        val schedules = mutableListOf<MedicationSchedule>()
        if (daysOrHour == "Hours") {
            repeat(24 / intervalHours) {
                schedules.add(
                    MedicationSchedule(
                        medicationId = medicationId,
                        medicationName = medicationName,
                        scheduledTime = nextTime.toString(),
                        date = date,
                        hoursOrDays = daysOrHour,
                        description = description,
                        intervalTime = intervalHours.toString()
                    )
                )
                nextTime = nextTime.plusHours(intervalHours.toLong())
            }
        } else {
            val futureDateTime = nextDate.plusDays(intervalHours.toLong())
            schedules.add(
                MedicationSchedule(
                    medicationId = medicationId,
                    medicationName = medicationName,
                    scheduledTime = nextTime.toString(),
                    date = futureDateTime.toString(),
                    intervalTime = intervalHours.toString(),
                    hoursOrDays = daysOrHour,
                    description = description
                )
            )
        }

        if (schedules.isNotEmpty()) {
            medicationScheduleRepository.insertSchedule(schedules)
        }

        return schedules
    }

}