package com.app.bamboo.domain.repositories.medications

import androidx.lifecycle.LiveData
import com.app.bamboo.data.models.medications.MedicationEntities
import kotlinx.coroutines.flow.Flow
import java.sql.Date

interface MedicationRepository {
    fun getAllMedications(): Flow<List<MedicationEntities>>

    suspend fun insertMedication(
        medicationName: String,
        description: String,
        pillOrDrop: String,
        daysOrHour: String,
        medicationTime: String,
        quantityThreshold: Int,
        date: String,
        quantity: Int,
        time: Long,
        amountMedication: Int
    ): Long

    suspend fun deleteMedication(id: Long)
    suspend fun updateMedication(
        id: Long,
        medicationName: String,
        description: String,
        pillOrDrop: String,
        daysOrHour: String,
        medicationTime: String,
        date: String,
        quantity: Int,
        time: Long,
        quantityThreshold: Int,
        amountMedication: Int
        )
    suspend fun updateDate(date: String, id: Long)

    suspend fun updateQuantity(quantity: Int, id: Long)

    suspend fun getIdIfQuantityLower() : List<MedicationEntities>

    suspend fun updateAccomplish(id: Long, accomplish: Boolean)

    fun getMedicationsTime(): LiveData<List<String>>

    suspend fun getMedicationsById(id: Long?) : List<MedicationEntities>
}