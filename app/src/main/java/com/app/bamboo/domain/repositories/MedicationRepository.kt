package com.app.bamboo.domain.repositories

import androidx.lifecycle.LiveData
import com.app.bamboo.data.models.MedicationEntities

interface MedicationRepository {
    suspend fun getAllMedications(): LiveData<List<MedicationEntities>>
    suspend fun insertMedication(
        medicationName: String,
        description: String,
        pillOrDrop: String,
        daysOrHour: String,
        medicationTime: Int,
    )

    suspend fun deleteMedication(id: Long)
    suspend fun updateMedication(
        medicationName: String,
        description: String,
        pillOrDrop: String,
        daysOrHour: String,
        medicationTime: Int,
    )

    suspend fun updateAccomplish(id: Long, accomplish: Boolean)
}