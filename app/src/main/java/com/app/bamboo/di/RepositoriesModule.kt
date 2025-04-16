package com.app.bamboo.di

import com.app.bamboo.data.database.dao.appointments.AppointmentDao
import com.app.bamboo.data.database.dao.LanguageDao
import com.app.bamboo.data.database.dao.medication.MedicationDao
import com.app.bamboo.data.database.dao.medication.MedicationHistoryDao
import com.app.bamboo.data.database.dao.medication.MedicationScheduleDao
import com.app.bamboo.data.repositoriesImpl.appointments.AppointmentRepositoryImpl
import com.app.bamboo.data.repositoriesImpl.LanguageRepositoryImpl
import com.app.bamboo.data.repositoriesImpl.medication.MedicationHistoryRepositoryImpl
import com.app.bamboo.data.repositoriesImpl.medication.MedicationRepositoryImpl
import com.app.bamboo.data.repositoriesImpl.medication.MedicationScheduleRepositoriesImpl
import com.app.bamboo.domain.repositories.appointments.AppointmentRepository
import com.app.bamboo.domain.repositories.LanguageRepository
import com.app.bamboo.domain.repositories.medications.MedicationHistoryRepository
import com.app.bamboo.domain.repositories.medications.MedicationRepository
import com.app.bamboo.domain.repositories.medications.MedicationScheduleRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoriesModule {

    @Provides
    @Singleton
    fun providerMedicationRepository(
        medicationDao: MedicationDao,
        medicationScheduleDao: MedicationScheduleDao,
    ): MedicationRepository {
        return MedicationRepositoryImpl(
            medicationDao = medicationDao,
        )
    }

    @Provides
    @Singleton
    fun providerAppointmentRepository(appointmentDao: AppointmentDao): AppointmentRepository {
        return AppointmentRepositoryImpl(appointmentDao = appointmentDao)
    }

    @Provides
    @Singleton
    fun providerLanguageRepository(languageDao: LanguageDao): LanguageRepository {
        return LanguageRepositoryImpl(languageDao = languageDao)
    }
    @Provides
    @Singleton
    fun providerMedicationScheduleRepository(medicationScheduleDao: MedicationScheduleDao): MedicationScheduleRepository {
        return MedicationScheduleRepositoriesImpl(medicationScheduleDao = medicationScheduleDao)
    }
    @Provides
    @Singleton
    fun providerMedicationHistoryRepository(medicationHistoryDao: MedicationHistoryDao): MedicationHistoryRepository {
        return MedicationHistoryRepositoryImpl(dao = medicationHistoryDao)
    }
}