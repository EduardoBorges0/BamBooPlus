package com.app.bamboo.domain.repositories

import com.app.bamboo.data.models.ThemeMode

interface ThemeModeRepository {
    suspend fun insertTheme(theme: ThemeMode)
    suspend fun getThemeMode(): Boolean
    suspend fun updateThemeMode(theme: Boolean)
}