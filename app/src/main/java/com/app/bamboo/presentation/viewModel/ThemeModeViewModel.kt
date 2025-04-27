package com.app.bamboo.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.bamboo.domain.repositories.ThemeModeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeModeViewModel @Inject constructor(private val repository: ThemeModeRepository): ViewModel() {
    private val _themeMode = MutableStateFlow<Boolean>(false)
    val themeMode : StateFlow<Boolean> = _themeMode

    fun getThemeMode(){
        viewModelScope.launch {
            _themeMode.value = repository.getThemeMode()
        }
    }
    fun updateThemeMode(theme: Boolean){
        viewModelScope.launch {
            repository.updateThemeMode(theme)
        }
    }
}