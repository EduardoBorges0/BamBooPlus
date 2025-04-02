package com.app.bamboo.data.database.settings

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object DatabaseProvider {
    val MIGRATION_4_5 = object : Migration(4, 5) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("""
            CREATE TABLE medication_schedule_new (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                medication_id INTEGER NOT NULL,
                scheduled_time TEXT NOT NULL,
                FOREIGN KEY(medication_id) REFERENCES medication_entity(id) ON DELETE CASCADE
            )
        """)

            // Copia os dados da tabela antiga para a nova tabela
            database.execSQL("""
            INSERT INTO medication_schedule_new (id, medication_id, scheduled_time)
            SELECT id, medication_id, scheduled_time FROM medication_schedule
        """)

            // Remove a tabela antiga
            database.execSQL("DROP TABLE medication_schedule")

            // Renomeia a nova tabela
            database.execSQL("ALTER TABLE medication_schedule_new RENAME TO medication_schedule")
        }
    }


    @Volatile
    private var INSTANCE: AppDatabase? = null

    private fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, "app_database"
            ).addMigrations(MIGRATION_4_5)
                .build().also { INSTANCE = it }
        }
    }
    fun getMedicationDao(context: Context) = getDatabase(context).medicationDao()
    fun getAppointmentDao(context: Context) = getDatabase(context).appointmentDao()
    fun getLanguageDao(context: Context) = getDatabase(context).languageDao()
    fun getMedicationScheduleDao(context: Context) = getDatabase(context).medicationSchedule()
}
