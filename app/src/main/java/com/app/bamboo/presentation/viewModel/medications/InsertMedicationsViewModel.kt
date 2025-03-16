package com.app.bamboo.presentation.viewModel.medications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.bamboo.domain.repositories.MedicationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InsertMedicationsViewModel @Inject constructor(private val repository: MedicationRepository) :
    ViewModel() {
    fun insertMedication(
        medicationName: String,
        description: String,
        pillOrDrop: String,
        daysOrHour: String,
        medicationTime: Int,
    ) {
        viewModelScope.launch {
            repository.insertMedication(
                medicationName = medicationName,
                description = description,
                pillOrDrop = pillOrDrop,
                daysOrHour = daysOrHour,
                medicationTime = medicationTime
            )
        }
    }
}