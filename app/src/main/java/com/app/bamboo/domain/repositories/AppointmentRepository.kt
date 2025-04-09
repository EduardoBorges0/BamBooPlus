package com.app.bamboo.domain.repositories

import androidx.lifecycle.LiveData
import com.app.bamboo.data.models.AppointmentEntities
import com.app.bamboo.data.models.AppointmentSummary

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

}