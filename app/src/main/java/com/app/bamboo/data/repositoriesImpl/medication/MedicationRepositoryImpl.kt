package com.app.bamboo.data.repositoriesImpl.medication

import androidx.lifecycle.LiveData
import com.app.bamboo.data.database.dao.medication.MedicationDao
import com.app.bamboo.data.models.medications.MedicationEntities
import com.app.bamboo.domain.repositories.medications.MedicationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import javax.inject.Inject

class MedicationRepositoryImpl @Inject constructor(
    private val medicationDao: MedicationDao,
) :MedicationRepository {

    override fun getAllMedications(): Flow<List<MedicationEntities>> {
        return medicationDao.getAllMedications()
    }

    override suspend fun insertMedication(
        medicationName: String,
        description: String,
        pillOrDrop: String,
        daysOrHour: String,
        medicationTime: String,
        quantityThreshold: Int,
        date: String,
        quantity: Int,
        time: Long,
    ): Long {
        val medication = MedicationEntities(
            medicationName = medicationName,
            description = description,
            pillOrDrop = pillOrDrop,
            daysOrHours = daysOrHour,
            medicationTime = medicationTime,
            date = date,
            quantity = quantity,
            time = time,
            quantityThreshold = quantityThreshold
        )
        return medicationDao.insertMedication(medication)
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
        date: String,
        quantity: Int,
        time: Long,
        quantityThreshold: Int,
    ) {
        val medication = MedicationEntities(
            medicationName = medicationName,
            description = description,
            pillOrDrop = pillOrDrop,
            daysOrHours = daysOrHour,
            medicationTime = medicationTime,
            date = date,
            quantity = quantity,
            time = time,
            quantityThreshold = quantityThreshold
        )
        medicationDao.updateMedication(medication)
    }

    override suspend fun updateDate(date: String, id: Long){
        medicationDao.updateDate(date, id)
    }


    override suspend fun updateAccomplish(id: Long, accomplish: Boolean) {
        medicationDao.updateAccomplish(id = id, accomplish = accomplish)
    }

    override fun getMedicationsTime(): LiveData<List<String>> {
        return medicationDao.getMedicationsTime()
    }

    override suspend fun getMedicationsById(id: Long?) : List<MedicationEntities>{
        return medicationDao.getMedicationsById(id)
    }
}