package com.app.bamboo.data.database.settings

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.bamboo.data.database.dao.AppointmentDao
import com.app.bamboo.data.database.dao.LanguageDao
import com.app.bamboo.data.database.dao.MedicationDao
import com.app.bamboo.data.database.dao.MedicationScheduleDao
import com.app.bamboo.data.models.AppointmentEntities
import com.app.bamboo.data.models.LanguageEntity
import com.app.bamboo.data.models.MedicationEntities
import com.app.bamboo.data.models.MedicationSchedule

@Database(
    entities = [MedicationEntities::class, AppointmentEntities::class, LanguageEntity::class, MedicationSchedule::class],
    version = 5
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun medicationDao(): MedicationDao
    abstract fun appointmentDao(): AppointmentDao
    abstract fun languageDao(): LanguageDao
    abstract fun medicationSchedule(): MedicationScheduleDao
}