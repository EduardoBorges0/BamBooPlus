package com.app.bamboo.data.repositoriesImpl.medication

import androidx.lifecycle.LiveData
import com.app.bamboo.data.database.dao.medication.MedicationScheduleDao
import com.app.bamboo.data.models.medications.MedicationSchedule
import com.app.bamboo.domain.repositories.medications.MedicationScheduleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MedicationScheduleRepositoriesImpl @Inject constructor(private val medicationScheduleDao: MedicationScheduleDao) :
    MedicationScheduleRepository {
    override fun getSchedulesForMedication(medicationId: Long): LiveData<List<String>> {
        return medicationScheduleDao.getSchedulesForMedication(medicationId)
    }

    override suspend fun insertSchedule(medicationList: List<MedicationSchedule>) {
        medicationScheduleDao.insertSchedule(medicationList)
    }

    override fun getAllSchedules(): LiveData<List<String>> {
        return medicationScheduleDao.getAllSchedules()
    }

    override fun getAllMedicationSchedules(): Flow<List<MedicationSchedule>> {
        return medicationScheduleDao.getAllMedicationSchedules()
    }

    override fun getAllMedicationId(): LiveData<List<Long>> {
        return medicationScheduleDao.getAllMedicationId()
    }

    override fun getMedicationsName(): LiveData<List<String>> {
        return medicationScheduleDao.getMedicationsName()
    }

    override suspend fun updateAccomplishSchedule(id: Long, accomplish: Boolean) {
        medicationScheduleDao.updateAccomplishSchedule(id, accomplish)
    }

    override fun getAllMedicationsScheduleById(id: Long): Flow<List<MedicationSchedule>>{
        return medicationScheduleDao.getAllMedicationsScheduleById(id)
    }

}