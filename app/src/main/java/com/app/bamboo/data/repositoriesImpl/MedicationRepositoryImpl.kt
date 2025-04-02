package com.app.bamboo.data.repositoriesImpl

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asFlow
import com.app.bamboo.data.database.dao.MedicationDao
import com.app.bamboo.data.database.dao.MedicationScheduleDao
import com.app.bamboo.data.models.MedicationEntities
import com.app.bamboo.data.models.MedicationSchedule
import com.app.bamboo.domain.repositories.MedicationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Dispatcher
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class MedicationRepositoryImpl @Inject constructor(
    private val medicationDao: MedicationDao,
) :
    MedicationRepository {
    override suspend fun getAllMedications(): LiveData<List<MedicationEntities>> {
        return medicationDao.getAllMedications()
    }

    override suspend fun insertMedication(
        medicationName: String,
        description: String,
        pillOrDrop: String,
        daysOrHour: String,
        medicationTime: String,
        time: Long,
    ): Long {
        val medication = MedicationEntities(
            medicationName = medicationName,
            description = description,
            pillOrDrop = pillOrDrop,
            daysOrHours = daysOrHour,
            medicationTime = medicationTime,
            time = time
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
        time: Long,
    ) {
        val medication = MedicationEntities(
            medicationName = medicationName,
            description = description,
            pillOrDrop = pillOrDrop,
            daysOrHours = daysOrHour,
            medicationTime = medicationTime,
            time = time
        )
        medicationDao.updateMedication(medication)
    }

    override suspend fun updateAccomplish(id: Long, accomplish: Boolean) {
        medicationDao.updateAccomplish(id = id, accomplish = accomplish)
    }

    override fun getMedicationsTime(): Flow<List<String>> {
        return medicationDao.getMedicationsTime().asFlow()
    }
}