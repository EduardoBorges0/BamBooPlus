package com.app.bamboo.data.repositoriesImpl

import androidx.lifecycle.LiveData
import com.app.bamboo.data.database.dao.MedicationScheduleDao
import com.app.bamboo.domain.repositories.MedicationScheduleRepository
import javax.inject.Inject

class MedicationScheduleRepositoriesImpl @Inject constructor(private val medicationScheduleDao: MedicationScheduleDao) :
    MedicationScheduleRepository {
    override fun getSchedulesForMedication(medicationId: Long): LiveData<List<String>> {
        return medicationScheduleDao.getSchedulesForMedication(medicationId)
    }
}