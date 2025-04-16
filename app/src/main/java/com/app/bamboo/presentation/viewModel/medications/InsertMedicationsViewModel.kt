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
                time = time
            )
            insertSchedules(
                medicationId,
                daysOrHour,
                date,
                medicationTime,
                time.toInt(),
                medicationName
            )
        }
    }

    suspend fun insertSchedules(
        medicationId: Long,
        daysOrHour: String,
        date: String,
        startTime: String,
        intervalHours: Int,
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
                    hoursOrDays = daysOrHour
                )
            )
        }

        if (schedules.isNotEmpty()) {
            medicationScheduleRepository.insertSchedule(schedules)
        }

        return schedules
    }

}