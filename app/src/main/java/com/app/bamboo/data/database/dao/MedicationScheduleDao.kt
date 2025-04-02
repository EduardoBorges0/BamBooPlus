package com.app.bamboo.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.bamboo.data.models.MedicationSchedule

@Dao
interface MedicationScheduleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSchedule(schedule: List<MedicationSchedule>)

    @Query("SELECT scheduled_time FROM medication_schedule WHERE medication_id = :medicationId")
    fun getSchedulesForMedication(medicationId: Long): LiveData<List<String>>

    @Query("SELECT scheduled_time FROM medication_schedule")
    fun getAllSchedules(): LiveData<List<String>>

    @Query("DELETE FROM medication_schedule WHERE medication_id = :medicationId")
    suspend fun deleteSchedules(medicationId: Long)
}
