package com.app.bamboo.presentation.viewModel.medications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.bamboo.data.models.MedicationEntities
import com.app.bamboo.domain.repositories.MedicationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Thread.State
import javax.inject.Inject

class MedicationsViewModel @Inject constructor(private val repository: MedicationRepository) :
    ViewModel() {
    private val _getAllMedications = MutableStateFlow<List<MedicationEntities>>(emptyList())
    val getAllMedications: StateFlow<List<MedicationEntities>> = _getAllMedications

    private val _getNextMedication = MutableStateFlow<List<MedicationEntities>>(emptyList())
    val getNextMedication: StateFlow<List<MedicationEntities>> = _getNextMedication

    fun getAllMedications() {
        viewModelScope.launch {
            repository.getAllMedications().collect { list ->
                _getAllMedications.value = list
            }
        }
    }

    suspend fun getNextMedication() {

    }

    suspend fun searchMedication() {

    }

    fun deleteMedications(id: Long){
        viewModelScope.launch {
            repository.deleteMedication(id = id)
        }
    }
}