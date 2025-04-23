package com.app.bamboo.data.database.dao.medication

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.bamboo.data.models.medications.MedicationEntities
import com.app.bamboo.data.models.medications.MedicationSchedule
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicationScheduleDao {
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertSchedule(schedule: List<MedicationSchedule>)

    @Query("SELECT scheduled_time FROM medication_schedule WHERE medication_id = :medicationId")
    fun getSchedulesForMedication(medicationId: Long): LiveData<List<String>>

    @Query("SELECT scheduled_time FROM medication_schedule")
    fun getAllSchedules(): LiveData<List<String>>

    @Query("SELECT * FROM medication_schedule WHERE id= :id")
    suspend fun getSchedulesMedicationsById(id: Long?) : List<MedicationSchedule>

    @Query("UPDATE medication_schedule SET accomplish = :accomplish WHERE id = :id")
    suspend fun updateAccomplishSchedule(id: Long, accomplish: Boolean?)

    @Query("UPDATE medication_schedule SET date = :date WHERE id = :id")
    suspend fun updateDateSchedule(id: Long, date: String)

    @Query("SELECT * FROM medication_schedule WHERE medication_id= :id")
    fun getAllMedicationsScheduleById(id: Long): Flow<List<MedicationSchedule>>

    @Query("SELECT medication_id FROM medication_schedule")
    fun getAllMedicationId(): LiveData<List<Long>>

    @Query("SELECT id FROM medication_schedule WHERE accomplish = 1")
    fun getAllId(): Flow<List<Long>>

    @Query("SELECT medication_name FROM medication_schedule")
    fun getMedicationsName(): LiveData<List<String>>

    @Query("SELECT * FROM medication_schedule")
    fun getAllMedicationSchedules(): Flow<List<MedicationSchedule>>

    @Query("DELETE FROM medication_schedule WHERE medication_id = :medicationId")
    suspend fun deleteSchedules(medicationId: Long)
}