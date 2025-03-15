package com.app.bamboo.di

import com.app.bamboo.data.database.dao.AppointmentDao
import com.app.bamboo.data.database.dao.LanguageDao
import com.app.bamboo.data.database.dao.MedicationDao
import com.app.bamboo.data.repositoriesImpl.AppointmentRepositoryImpl
import com.app.bamboo.data.repositoriesImpl.LanguageRepositoryImpl
import com.app.bamboo.data.repositoriesImpl.MedicationRepositoryImpl
import com.app.bamboo.domain.repositories.AppointmentRepository
import com.app.bamboo.domain.repositories.LanguageRepository
import com.app.bamboo.domain.repositories.MedicationRepository
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
    fun providerMedicationRepository(medicationDao: MedicationDao): MedicationRepository {
        return MedicationRepositoryImpl(medicationDao = medicationDao)
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
}