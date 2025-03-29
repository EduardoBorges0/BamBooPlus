package com.app.bamboo.domain.usecases

import com.app.bamboo.domain.repositories.MedicationRepository
import com.app.bamboo.utils.TimeUtils
import java.time.Duration
import java.time.LocalTime
import javax.inject.Inject

class SortedListMedicationsUseCase @Inject constructor(private val repository: MedicationRepository) {
    suspend operator fun invoke(): List<String> {
        val medications = repository.getMedicationsTime()
        val now = LocalTime.now()

        return medications
            .map { TimeUtils.formattedLocalDate(it) }
            .sortedBy {
                val diff = Duration.between(now, it).seconds
                if (diff < 0) Duration.between(now, it.plusHours(24)).seconds else diff
            }
            .map { it.format(TimeUtils.formatter) }
    }
}
