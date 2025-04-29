package com.app.bamboo.data.database.settings

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase.Callback
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.app.bamboo.data.models.ThemeMode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object DatabaseProvider {
    val MIGRATION_15_16 = object : Migration(15, 16) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("CREATE TABLE IF NOT EXISTS theme_mode (\n" +
                    "    id INTEGER PRIMARY KEY NOT NULL,\n" +
                    "    theme INTEGER NOT NULL\n" +
                    ");\n")
        }
    }

    @Volatile
    private var INSTANCE: AppDatabase? = null

    private fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, "app_database"
            ).addMigrations(MIGRATION_15_16).addCallback(object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    CoroutineScope(Dispatchers.IO).launch {
                        INSTANCE?.themeDao()?.insertTheme(
                            ThemeMode(theme = false)
                        )
                    }
                }
            })
                .build().also { INSTANCE = it }
        }
    }

    fun getMedicationDao(context: Context) = getDatabase(context).medicationDao()
    fun getAppointmentDao(context: Context) = getDatabase(context).appointmentDao()
    fun getLanguageDao(context: Context) = getDatabase(context).languageDao()
    fun getThemeDao(context: Context) = getDatabase(context).themeDao()
    fun getMedicationScheduleDao(context: Context) = getDatabase(context).medicationSchedule()
    fun getMedicationHistoryDao(context: Context) = getDatabase(context).medicationHistoryDao()

}
