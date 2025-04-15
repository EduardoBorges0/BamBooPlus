package com.app.bamboo.domain.repositories

import androidx.lifecycle.LiveData
import com.app.bamboo.data.models.MedicationEntities
import kotlinx.coroutines.flow.Flow

interface MedicationRepository {
    suspend fun getAllMedications(): LiveData<List<MedicationEntities>>

    suspend fun insertMedication(
        medicationName: String,
        description: String,
        pillOrDrop: String,
        daysOrHour: String,
        medicationTime: String,
        time: Long
    ): Long

    suspend fun deleteMedication(id: Long)
    suspend fun updateMedication(
        medicationName: String,
        description: String,
        pillOrDrop: String,
        daysOrHour: String,
        medicationTime: String,
        time: Long
    )

    suspend fun updateAccomplish(id: Long, accomplish: Boolean)

    fun getMedicationsTime(): LiveData<List<String>>

    suspend fun getMedicationsById(id: Long?) : List<MedicationEntities>
}