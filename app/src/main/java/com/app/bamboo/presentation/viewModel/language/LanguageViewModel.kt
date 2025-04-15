package com.app.bamboo.presentation.viewModel.language

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.bamboo.data.models.LanguageEntity
import com.app.bamboo.domain.repositories.LanguageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(
    private val repository: LanguageRepository
) : ViewModel() {

    fun insertLanguage(languageCode: String) {
        viewModelScope.launch {
            repository.insertLanguage(languageCode)
        }
    }

    fun updateLanguage(language: String) {
        viewModelScope.launch {
            repository.updateLanguage(language)
        }
    }

    fun getLanguage(): LiveData<LanguageEntity?> {
        return repository.getLanguage()
    }
}
