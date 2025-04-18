package com.app.bamboo.domain.repositories.medications

import androidx.lifecycle.LiveData
import com.app.bamboo.data.models.medications.MedicationHistoryEntities
import java.util.ListResourceBundle

interface MedicationHistoryRepository {
    suspend fun insertMedication(
        medicationId: Long,
        medicationName: String,
        medicationTime: String,
        dayOfWeek: String
    ): Long
    fun getMedicationHistoryByDayOfWeek(selectedDay: String): LiveData<List<MedicationHistoryEntities>>

    suspend fun deleteAllMedicationHistory()

}