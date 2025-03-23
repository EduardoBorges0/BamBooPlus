package com.app.bamboo.data.repositoriesImpl

import androidx.lifecycle.LiveData
import com.app.bamboo.data.database.dao.MedicationDao
import com.app.bamboo.data.models.MedicationEntities
import com.app.bamboo.domain.repositories.MedicationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class MedicationRepositoryImpl @Inject constructor(private val medicationDao: MedicationDao) :
    MedicationRepository {
    override suspend fun getAllMedications(): Flow<List<MedicationEntities>> {
        return medicationDao.getAllMedications()
    }

    override suspend fun insertMedication(
        medicationName: String,
        description: String,
        pillOrDrop: String,
        daysOrHour: String,
        medicationTime: String,
    ) {
        val medication = MedicationEntities(
            medicationName = medicationName,
            description = description,
            pillOrDrop = pillOrDrop,
            daysOrHours = daysOrHour,
            medicationTime = medicationTime
        )
        medicationDao.insertMedication(medication)
    }

    override suspend fun deleteMedication(id: Long) {
        medicationDao.deleteMedication(id)
    }

    override suspend fun updateMedication(
        medicationName: String,
        description: String,
        pillOrDrop: String,
        daysOrHour: String,
        medicationTime: String,
    ) {
        val medication = MedicationEntities(
            medicationName = medicationName,
            description = description,
            pillOrDrop = pillOrDrop,
            daysOrHours = daysOrHour,
            medicationTime = medicationTime
        )
        medicationDao.updateMedication(medication)
    }

    override suspend fun updateAccomplish(id: Long, accomplish: Boolean) {
        medicationDao.updateAccomplish(id = id, accomplish = accomplish)
    }
    override suspend fun getMedicationsTime(): List<String>{
        return medicationDao.getMedicationsTime()
    }
    override suspend fun getIdByMedicationsTime(medicationTime: String): Long{
        return medicationDao.getIdByMedicationsTime(medicationTime)
    }

}