package com.app.bamboo.domain.repositories

import androidx.lifecycle.LiveData
import com.app.bamboo.data.models.MedicationSchedule

interface MedicationScheduleRepository {
    fun getAllSchedules(): LiveData<List<String>>
    fun getSchedulesForMedication(medicationId: Long): LiveData<List<String>>
    suspend fun insertSchedule(medicationList: List<MedicationSchedule>)
    fun getAllMedicationSchedules(): LiveData<List<MedicationSchedule>>
    fun getAllMedicationId(): LiveData<List<Long>>
    fun getMedicationsName(): LiveData<List<String>>

}