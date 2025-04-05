package com.app.bamboo.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.app.bamboo.data.models.MedicationEntities

@Dao
interface MedicationDao {
    @Query("SELECT * FROM medication_entity")
    fun getAllMedications() : LiveData<List<MedicationEntities>>

    @Query("SELECT * FROM medication_entity WHERE id= :id")
    suspend fun getMedicationsById(id: Long?) : List<MedicationEntities>

    @Insert
    suspend fun insertMedication(medicationEntities: MedicationEntities) : Long

    @Query("DELETE FROM medication_entity WHERE id = :id")
    suspend fun deleteMedication(id: Long)

    @Update
    suspend fun updateMedication(medication: MedicationEntities)

    @Query("UPDATE medication_entity SET accomplish = :accomplish WHERE id = :id")
    suspend fun updateAccomplish(id: Long, accomplish: Boolean)

    @Query("SELECT medication_time FROM medication_entity")
    fun getMedicationsTime(): LiveData<List<String>>
}