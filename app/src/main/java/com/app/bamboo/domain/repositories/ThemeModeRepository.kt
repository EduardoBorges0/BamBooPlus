package com.app.bamboo.domain.repositories

interface ThemeModeRepository {
    suspend fun getThemeMode(): Boolean
    suspend fun updateThemeMode(theme: Boolean)
}