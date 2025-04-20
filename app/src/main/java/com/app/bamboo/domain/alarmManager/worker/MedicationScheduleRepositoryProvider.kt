package com.app.bamboo.domain.alarmManager.worker

import android.content.Context
import com.app.bamboo.data.database.settings.AppDatabase
import com.app.bamboo.data.database.settings.DatabaseProvider
import com.app.bamboo.data.repositoriesImpl.medication.MedicationScheduleRepositoriesImpl
import com.app.bamboo.domain.repositories.medications.MedicationScheduleRepository

object MedicationScheduleRepositoryProvider {
    fun getRepository(context: Context): MedicationScheduleRepository {
        val database = DatabaseProvider.getMedicationScheduleDao(context)
        return MedicationScheduleRepositoriesImpl(database)
    }
}
