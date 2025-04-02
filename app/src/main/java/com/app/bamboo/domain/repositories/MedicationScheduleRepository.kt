package com.app.bamboo.domain.repositories

import androidx.lifecycle.LiveData

interface MedicationScheduleRepository {
    fun getSchedulesForMedication(medicationId: Long): LiveData<List<String>>
}