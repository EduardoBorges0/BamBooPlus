package com.app.bamboo.di

import android.content.Context
import com.app.bamboo.data.database.dao.AppointmentDao
import com.app.bamboo.data.database.dao.LanguageDao
import com.app.bamboo.data.database.dao.MedicationDao
import com.app.bamboo.data.database.settings.DatabaseProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {
    @Provides
    @Singleton
    fun providerAppointmentDao(@ApplicationContext context: Context): AppointmentDao {
        return DatabaseProvider.getAppointmentDao(context)
    }

    @Provides
    @Singleton
    fun providerMedicationDao(@ApplicationContext context: Context): MedicationDao {
        return DatabaseProvider.getMedicationDao(context)
    }

    @Provides
    @Singleton
    fun providerLanguageDao(@ApplicationContext context: Context): LanguageDao {
        return DatabaseProvider.getLanguageDao(context)
    }
}
