package com.app.bamboo.data.repositoriesImpl

import com.app.bamboo.data.database.dao.ThemeDao
import com.app.bamboo.domain.repositories.ThemeModeRepository
import javax.inject.Inject

class ThemeModeRepositoryImpl @Inject constructor(private val themeDao: ThemeDao) :
    ThemeModeRepository {
    override suspend fun updateThemeMode(theme: Boolean) {
        themeDao.updateThemeMode(theme)
    }

    override suspend fun getThemeMode(): Boolean {
        return themeDao.getThemeMode()
    }
}