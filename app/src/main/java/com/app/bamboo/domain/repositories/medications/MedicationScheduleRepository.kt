package com.app.bamboo.domain.repositories.medications

import androidx.lifecycle.LiveData
import com.app.bamboo.data.models.medications.MedicationSchedule
import kotlinx.coroutines.flow.Flow

interface MedicationScheduleRepository {
    fun getAllSchedules(): LiveData<List<String>>
    fun getSchedulesForMedication(medicationId: Long): LiveData<List<String>>
    suspend fun insertSchedule(medicationList: List<MedicationSchedule>)
    fun getAllMedicationSchedules(): Flow<List<MedicationSchedule>>
    fun getAllMedicationId(): LiveData<List<Long>>
    fun getMedicationsName(): LiveData<List<String>>
    suspend fun updateAccomplishSchedule(id: Long, accomplish: Boolean)
    fun getAllMedicationsScheduleById(id: Long): Flow<List<MedicationSchedule>>

}