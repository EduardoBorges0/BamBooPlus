package com.app.bamboo.data.database.dao.medication

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.app.bamboo.data.models.medications.MedicationHistoryEntities

@Dao
interface MedicationHistoryDao {
    @Insert
    suspend fun insertMedicationHistory(medicationHistoryEntities: MedicationHistoryEntities): Long

    @Query("SELECT * FROM medication_history WHERE day_of_week = :selectedDay")
    fun getMedicationHistory(selectedDay: String): LiveData<List<MedicationHistoryEntities>>

    @Query("DELETE FROM medication_history")
    suspend fun deleteAllMedicationHistory()
}