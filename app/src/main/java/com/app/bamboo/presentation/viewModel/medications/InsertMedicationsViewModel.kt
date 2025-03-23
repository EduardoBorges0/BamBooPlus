package com.app.bamboo.presentation.viewModel.medications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.bamboo.domain.repositories.MedicationRepository
import com.app.bamboo.utils.TimeUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class InsertMedicationsViewModel @Inject constructor(private val repository: MedicationRepository) :
    ViewModel() {
    fun insertMedication(
        medicationName: String,
        description: String,
        pillOrDrop: String,
        daysOrHour: String,
        medicationTime: String,
        time: Long
    ) {
        viewModelScope.launch {
            val convertTime = LocalTime.parse(medicationTime).plusHours(time)
            repository.insertMedication(
                medicationName = medicationName,
                description = description,
                pillOrDrop = pillOrDrop,
                daysOrHour = daysOrHour,
                medicationTime = convertTime.toString()
            )
        }
    }
}