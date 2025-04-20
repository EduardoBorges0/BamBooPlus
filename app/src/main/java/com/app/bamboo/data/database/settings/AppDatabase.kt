package com.app.bamboo.data.database.settings

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.bamboo.data.database.dao.appointments.AppointmentDao
import com.app.bamboo.data.database.dao.LanguageDao
import com.app.bamboo.data.database.dao.medication.MedicationDao
import com.app.bamboo.data.database.dao.medication.MedicationHistoryDao
import com.app.bamboo.data.database.dao.medication.MedicationScheduleDao
import com.app.bamboo.data.models.appointments.AppointmentEntities
import com.app.bamboo.data.models.LanguageEntity
import com.app.bamboo.data.models.medications.MedicationEntities
import com.app.bamboo.data.models.medications.MedicationHistoryEntities
import com.app.bamboo.data.models.medications.MedicationSchedule

@Database(
    entities = [
        MedicationEntities::class,
        AppointmentEntities::class,
        LanguageEntity::class,
        MedicationSchedule::class,
        MedicationHistoryEntities::class],
    version = 11
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun medicationDao(): MedicationDao
    abstract fun appointmentDao(): AppointmentDao
    abstract fun languageDao(): LanguageDao
    abstract fun medicationSchedule(): MedicationScheduleDao
    abstract fun medicationHistoryDao(): MedicationHistoryDao
}