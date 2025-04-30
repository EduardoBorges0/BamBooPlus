package com.app.bamboo.data.repositoriesImpl.appointments

import androidx.lifecycle.LiveData
import com.app.bamboo.data.database.dao.appointments.AppointmentDao
import com.app.bamboo.data.models.appointments.AppointmentEntities
import com.app.bamboo.data.models.appointments.AppointmentSummary
import com.app.bamboo.domain.repositories.appointments.AppointmentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class AppointmentRepositoryImpl @Inject constructor(private val appointmentDao: AppointmentDao) :
    AppointmentRepository {
    override fun getAllAppointment(): Flow<List<AppointmentEntities>> {
        return appointmentDao.getAllAppointment()
    }

    override suspend fun insertAppointment(
        appointmentType: String,
        onlineOrOnSite: String,
        doctorName: String,
        hospitalName: String,
        appointmentDate: String,
        appointmentTime: String,
    ) {
        val appointment = AppointmentEntities(
            appointmentType = appointmentType,
            onlineOrOnSite = onlineOrOnSite,
            doctorName = doctorName,
            hospitalName = hospitalName,
            appointmentDate = appointmentDate,
            appointmentTime = appointmentTime
        )
        appointmentDao.insertAppointment(appointment)
    }

    override suspend fun updateAppointment(
        appointmentType: String,
        onlineOrOnSite: String,
        doctorName: String,
        hospitalName: String,
        appointmentDate: String,
        appointmentTime: String,
    ) {
        val appointment = AppointmentEntities(
            appointmentType = appointmentType,
            onlineOrOnSite = onlineOrOnSite,
            doctorName = doctorName,
            hospitalName = hospitalName,
            appointmentDate = appointmentDate,
            appointmentTime = appointmentTime
        )
        appointmentDao.updateAppointment(appointment)
    }

    override suspend fun deleteAppointment(id: Long) {
        appointmentDao.deleteAppointment(id)
    }

    override suspend fun updateAccomplish(id: Long, accomplish: Boolean) {
        appointmentDao.updateAccomplish(id = id, accomplish = accomplish)
    }

    override fun getAppointmentSummaries(): LiveData<List<AppointmentSummary>> {
        return appointmentDao.getAppointmentSummaries()
    }

    override suspend fun getAppointmentsById(id: Long): Flow<List<AppointmentEntities>> {
        return appointmentDao.getAppointmentsById(id)
    }
}