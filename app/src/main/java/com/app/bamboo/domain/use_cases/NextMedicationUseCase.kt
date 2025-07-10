package com.app.bamboo.domain.use_cases

import com.app.bamboo.data.models.medications.MedicationSchedule
import com.app.bamboo.utils.TimeUtils.parseToLocalDateTime
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import java.time.LocalDateTime
import java.util.Date

class NextMedicationUseCase {
    suspend operator fun invoke(
        medicationScheduleTimes: StateFlow<List<MedicationSchedule>>,
        currentDateTime: LocalDateTime = LocalDateTime.now()
    ) : List<MedicationSchedule>{
        val upcomingMedications = medicationScheduleTimes.first().let { list ->
            val scheduledDateTimes = list.map {
                val fullDateTime = parseToLocalDateTime(it.date, it.scheduledTime)
                it to fullDateTime
            }
            val futureMedications = scheduledDateTimes.filter { (_, dateTime) ->
                dateTime.isAfter(currentDateTime)
            }
            if (futureMedications.isNotEmpty()) {
                val nextTime = futureMedications.minByOrNull { it.second }?.second
                futureMedications.filter { it.second == nextTime }.map { it.first }
            } else {
                val earliestTime = scheduledDateTimes.minByOrNull { it.second }?.second
                scheduledDateTimes.filter { it.second == earliestTime }.map { it.first }
            }
        }
        return upcomingMedications
    }
}