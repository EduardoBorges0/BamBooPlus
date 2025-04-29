package com.app.bamboo.data.database.dao.appointments

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.app.bamboo.data.models.appointments.AppointmentEntities
import com.app.bamboo.data.models.appointments.AppointmentSummary
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Dao
interface AppointmentDao {
    @Query("SELECT * FROM appointment_entity")
    fun getAllAppointment(): Flow<List<AppointmentEntities>>

    @Insert
    suspend fun insertAppointment(appointment: AppointmentEntities)

    @Query("DELETE FROM appointment_entity WHERE id = :id")
    suspend fun deleteAppointment(id: Long)

    @Update
    suspend fun updateAppointment(appointment: AppointmentEntities)

    @Query("UPDATE appointment_entity SET accomplish = :accomplish WHERE id = :id")
    suspend fun updateAccomplish(id: Long, accomplish: Boolean)

    @Query("SELECT * FROM appointment_entity WHERE id= :id")
    suspend fun getAppointmentsById(id: Long): List<AppointmentEntities>

    @Query(
        """
        SELECT id, appointment_type, appointment_date, appointment_time 
        FROM appointment_entity  WHERE accomplish IS NULL
    """
    )
     fun getAppointmentSummaries(): LiveData<List<AppointmentSummary>>
}