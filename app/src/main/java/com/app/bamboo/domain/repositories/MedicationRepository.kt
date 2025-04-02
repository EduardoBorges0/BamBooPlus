package com.app.bamboo.domain.repositories

import androidx.lifecycle.LiveData
import androidx.room.Query
import com.app.bamboo.data.models.MedicationEntities
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

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

    fun getMedicationsTime(): Flow<List<String>>
}