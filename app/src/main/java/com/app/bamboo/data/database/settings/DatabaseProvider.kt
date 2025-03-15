package com.app.bamboo.data.database.settings

import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    @Volatile
    private var INSTANCE: AppDatabase? = null

    private fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, "app_database"
            )
                .build().also { INSTANCE = it }
        }
    }
    fun getMedicationDao(context: Context) = getDatabase(context).medicationDao()
    fun getAppointmentDao(context: Context) = getDatabase(context).appointmentDao()
    fun getLanguageDao(context: Context) = getDatabase(context).languageDao()
}
