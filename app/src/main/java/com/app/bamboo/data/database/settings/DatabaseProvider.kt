package com.app.bamboo.data.database.settings

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object DatabaseProvider {
    val MIGRATION_5_6 = object : Migration(5, 6) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                "ALTER TABLE medication_schedule ADD COLUMN medication_name TEXT NOT NULL DEFAULT ''"

            )
        }
    }


    @Volatile
    private var INSTANCE: AppDatabase? = null

    private fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, "app_database"
            ).addMigrations(MIGRATION_5_6)
                .build().also { INSTANCE = it }
        }
    }

    fun getMedicationDao(context: Context) = getDatabase(context).medicationDao()
    fun getAppointmentDao(context: Context) = getDatabase(context).appointmentDao()
    fun getLanguageDao(context: Context) = getDatabase(context).languageDao()
    fun getMedicationScheduleDao(context: Context) = getDatabase(context).medicationSchedule()
}
