package com.app.bamboo.domain.usecases

import com.app.bamboo.domain.repositories.MedicationRepository
import java.time.Duration
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class GetSortedMedicationsUseCase @Inject constructor(private val repository: MedicationRepository) {
    private val formatter = DateTimeFormatter.ofPattern("HH:mm")

    suspend operator fun invoke(): List<String> {
        val medications = repository.getMedicationsTime()
        val now = LocalTime.now()

        return medications
            .map { LocalTime.parse(it, formatter) }
            .sortedBy {
                val diff = Duration.between(now, it).seconds
                if (diff < 0) Duration.between(now, it.plusHours(24)).seconds else diff
            }
            .map { it.format(formatter) }
    }
}
