package com.app.bamboo.presentation.viewModel.medications

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.bamboo.data.models.medications.MedicationSchedule
import com.app.bamboo.domain.repositories.medications.MedicationRepository
import com.app.bamboo.domain.repositories.medications.MedicationScheduleRepository
import com.app.bamboo.presentation.view.stateClass.MedicationBasicState
import com.app.bamboo.presentation.view.stateClass.MedicationPillOrDropState
import com.app.bamboo.presentation.view.stateClass.MedicationScheduleState
import com.app.bamboo.utils.TimeUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class InsertMedicationsViewModel @Inject constructor(
    private val repository: MedicationRepository,
    private val medicationScheduleRepository: MedicationScheduleRepository,
) : ViewModel() {
    private val _basicState = MutableStateFlow(MedicationBasicState())
    val basicState: StateFlow<MedicationBasicState> = _basicState

    private val _pillOrDropState = MutableStateFlow(MedicationPillOrDropState())
    val pillOrDropState: StateFlow<MedicationPillOrDropState> = _pillOrDropState

    private val _scheduleState = MutableStateFlow(MedicationScheduleState())
    val scheduleState: StateFlow<MedicationScheduleState> = _scheduleState

    fun updateMedicationsBasic(name: String, function: String, stock: Long, warningStock: Int) {
        _basicState.update {
            it.copy(
                name = name,
                function = function,
                stock = stock,
                warningStock = warningStock
            )
        }
    }

    fun updatePillOrDrop(pillOrDrop: String, quantity: Int) {
        _pillOrDropState.update { it.copy(pillOrDrop = pillOrDrop, quantity = quantity) }
    }

    fun updateSchedule(
        startDate: String,
        startTime: String,
        intervalType: String,
        intervalValue: Long
    ) {
        _scheduleState.update {
            it.copy(
                startDate = startDate,
                startTime = startTime,
                intervalValue = intervalValue,
                intervalType = intervalType
            )
        }
    }

    fun insertMedication(
        medicationName: String,
        description: String,
        pillOrDrop: String,
        daysOrHour: String,
        medicationTime: String,
        date: String,
        quantityThreshold: Int,
        quantity: Int,
        time: Long,
        amountMedication: Int
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
                time = time,
                quantityThreshold = quantityThreshold,
                amountMedication = amountMedication
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

        Log.d("InsertMedicationsViewModel", "Next Time: $nextTime, Next Date: $nextDate")
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