package com.app.bamboo.domain.repositories.appointments

import androidx.lifecycle.LiveData
import com.app.bamboo.data.models.appointments.AppointmentEntities
import com.app.bamboo.data.models.appointments.AppointmentSummary
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface AppointmentRepository {
    fun getAllAppointment(): Flow<List<AppointmentEntities>>
    suspend fun insertAppointment(
        appointmentType: String,
        onlineOrOnSite: String,
        doctorName: String,
        hospitalName: String,
        appointmentDate: String,
        appointmentTime: String,
    )

    suspend fun updateAppointment(
        appointmentType: String,
        onlineOrOnSite: String,
        doctorName: String,
        hospitalName: String,
        appointmentDate: String,
        appointmentTime: String,
    )

    suspend fun deleteAppointment(id: Long)
    suspend fun updateAccomplish(id: Long, accomplish: Boolean)
    fun getAppointmentSummaries(): LiveData<List<AppointmentSummary>>
    suspend fun getAppointmentsById(id: Long): Flow<List<AppointmentEntities>>


}