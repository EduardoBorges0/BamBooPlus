package com.app.bamboo.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.app.bamboo.data.models.AppointmentEntities
import com.app.bamboo.data.models.MedicationEntities

@Dao
interface AppointmentDao {
    @Query("SELECT * FROM appointment_entity")
    fun getAllAppointment() : LiveData<List<AppointmentEntities>>

    @Insert
    suspend fun insertAppointment(appointment: AppointmentEntities)

    @Query("DELETE FROM appointment_entity WHERE id = :id")
    suspend fun deleteAppointment(id: Long)

    @Update
    suspend fun updateAppointment(appointment: AppointmentEntities)

    @Query("UPDATE appointment_entity SET accomplish = :accomplish WHERE id = :id")
    suspend fun updateAccomplish(id: Long, accomplish: Boolean)
}