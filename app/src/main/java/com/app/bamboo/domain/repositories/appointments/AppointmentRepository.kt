package com.app.bamboo.domain.repositories.appointments

import androidx.lifecycle.LiveData
import com.app.bamboo.data.models.appointments.AppointmentEntities
import com.app.bamboo.data.models.appointments.AppointmentSummary

interface AppointmentRepository {
    suspend fun getAllAppointment(): LiveData<List<AppointmentEntities>>
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
    suspend fun getAppointmentsById(id: Long): List<AppointmentEntities>


}