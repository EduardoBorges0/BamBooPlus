package com.app.bamboo.domain.repositories

import androidx.lifecycle.LiveData
import com.app.bamboo.data.models.LanguageEntity

interface LanguageRepository {
    suspend fun insertLanguage(languageCode: String)
    suspend fun updateLanguage(language: String)
    suspend fun getLanguage(): LiveData<LanguageEntity?>
}