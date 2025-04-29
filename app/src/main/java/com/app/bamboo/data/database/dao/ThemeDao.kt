package com.app.bamboo.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.app.bamboo.data.models.ThemeMode

@Dao
interface ThemeDao {
    @Insert
    suspend fun insertTheme(theme: ThemeMode)

    @Query("SELECT theme FROM theme_mode WHERE id = 1")
    suspend fun getThemeMode(): Boolean

    @Query("UPDATE theme_mode SET theme= :theme WHERE id = 1")
    suspend fun updateThemeMode(theme: Boolean)
}