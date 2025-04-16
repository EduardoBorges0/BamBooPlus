package com.app.bamboo.domain.repositories.medications

import androidx.lifecycle.LiveData
import com.app.bamboo.data.models.medications.MedicationEntities
import java.sql.Date

interface MedicationRepository {
    suspend fun getAllMedications(): LiveData<List<MedicationEntities>>

    suspend fun insertMedication(
        medicationName: String,
        description: String,
        pillOrDrop: String,
        daysOrHour: String,
        medicationTime: String,
        date: String,
        time: Long
    ): Long

    suspend fun deleteMedication(id: Long)
    suspend fun updateMedication(
        medicationName: String,
        description: String,
        pillOrDrop: String,
        daysOrHour: String,
        medicationTime: String,
        date: String,
        time: Long
    )
    suspend fun updateDate(date: String, id: Long)

    suspend fun updateAccomplish(id: Long, accomplish: Boolean)

    fun getMedicationsTime(): LiveData<List<String>>

    suspend fun getMedicationsById(id: Long?) : List<MedicationEntities>
}