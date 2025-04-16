package com.app.bamboo.data.repositoriesImpl.medication

import androidx.lifecycle.LiveData
import com.app.bamboo.data.database.dao.medication.MedicationHistoryDao
import com.app.bamboo.data.models.medications.MedicationHistoryEntities
import com.app.bamboo.domain.repositories.medications.MedicationHistoryRepository
import javax.inject.Inject

class MedicationHistoryRepositoryImpl @Inject constructor(private val dao: MedicationHistoryDao) :
    MedicationHistoryRepository {
    override suspend fun insertMedication(
        medicationId: Long,
        medicationName: String,
        medicationTime: String,
        dayOfWeek: String
    ): Long {
        val medicationHistory = MedicationHistoryEntities(
            medicationId = medicationId,
            medicationName = medicationName,
            medicationTime = medicationTime,
            dayOfWeek = dayOfWeek
        )
        return dao.insertMedicationHistory(medicationHistory)
    }

    override fun getMedicationHistoryByDayOfWeek(selectedDay: String): LiveData<List<MedicationHistoryEntities>> {
        return dao.getMedicationHistory(selectedDay)
    }
}