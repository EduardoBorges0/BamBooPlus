package com.app.bamboo.presentation.viewModel.language

import androidx.lifecycle.ViewModel
import com.app.bamboo.domain.repositories.LanguageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(private val repository: LanguageRepository) :
    ViewModel() {
}