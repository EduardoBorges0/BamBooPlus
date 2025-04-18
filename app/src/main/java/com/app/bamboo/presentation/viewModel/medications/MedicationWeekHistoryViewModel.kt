package com.app.bamboo.presentation.viewModel.medications

import androidx.lifecycle.ViewModel
import com.app.bamboo.domain.repositories.medications.MedicationHistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MedicationWeekHistoryViewModel @Inject constructor(private val medicationHistoryRepository: MedicationHistoryRepository) :
    ViewModel() {

}