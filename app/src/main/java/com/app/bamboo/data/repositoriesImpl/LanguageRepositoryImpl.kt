package com.app.bamboo.data.repositoriesImpl

import androidx.lifecycle.LiveData
import com.app.bamboo.data.database.dao.LanguageDao
import com.app.bamboo.data.models.LanguageEntity
import com.app.bamboo.domain.repositories.LanguageRepository
import javax.inject.Inject

class LanguageRepositoryImpl @Inject constructor(private val languageDao: LanguageDao) :
    LanguageRepository {
    override suspend fun insertLanguage(languageCode: String) {
        val language = LanguageEntity(
            language = languageCode
        )
        languageDao.insertLanguage(language)
    }

    override suspend fun updateLanguage(language: String) {
        languageDao.updateLanguage(language)
    }

    override fun getLanguage(): LiveData<LanguageEntity?> {
        return languageDao.getLanguage()
    }
}