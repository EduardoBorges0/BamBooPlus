package com.app.bamboo.data.models

import androidx.compose.runtime.Composable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "theme_mode")
data class ThemeMode(
    @PrimaryKey(autoGenerate = false) val id: Long = 1,
    @ColumnInfo("theme") val theme: Boolean
)