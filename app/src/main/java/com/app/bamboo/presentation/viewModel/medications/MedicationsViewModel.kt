package com.app.bamboo.presentation.viewModel.medications

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.app.bamboo.data.models.medications.MedicationEntities
import com.app.bamboo.data.models.medications.MedicationSchedule
import com.app.bamboo.domain.repositories.medications.MedicationHistoryRepository
import com.app.bamboo.domain.repositories.medications.MedicationRepository
import com.app.bamboo.domain.repositories.medications.MedicationScheduleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.lang.Thread.State
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class MedicationsViewModel @Inject constructor(
    private val repository: MedicationRepository,
    private val repositorySchedule: MedicationScheduleRepository,
    private val medicationHistoryRepository: MedicationHistoryRepository
) : ViewModel() {
    private val _getAllMedications = MutableStateFlow<List<MedicationEntities>>(emptyList())
    val getAllMedications: Flow<List<MedicationEntities>> = _getAllMedications

    private val _getMedicationsTimeById = MutableStateFlow<List<MedicationEntities>>(emptyList())
    val getMedicationsTimeById: Flow<List<MedicationEntities>> = _getMedicationsTimeById

    private val _getPercentById = MutableStateFlow<Float>(0f)
    val getPercentById: Flow<Float> = _getPercentById

    private val _getNextMedication = MutableLiveData<MedicationSchedule>()
    val getNextMedication: LiveData<MedicationSchedule> = _getNextMedication

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _getScheduleContainsAccomplishTrue = MutableLiveData<List<MedicationSchedule>>()
    val getScheduleContainsAccomplishTrue: LiveData<List<MedicationSchedule>> = _getScheduleContainsAccomplishTrue

    internal val medicationTimes = MutableLiveData<List<MedicationSchedule>>()


    fun getAllMedications(): Flow<List<MedicationEntities>>  {
        viewModelScope.launch {
            repository.getAllMedications().collect { medications ->
                _getAllMedications.value = medications
            }
        }
        return getAllMedications
    }

    fun getScheduleContainsAccomplishTrue(id: Long) {
        viewModelScope.launch {
            repositorySchedule.getAllMedicationsScheduleById(id).collect { list ->
                _getScheduleContainsAccomplishTrue.value =
                    list.filter { it.accomplish == true && it.id == id }
            }
        }
    }


    fun percentMedicationsTrue(id: Long) {
        viewModelScope.launch {
            val schedules = repositorySchedule.getAllMedicationsScheduleById(id).first()
            val trueAccomplishedSize = getScheduleContainsAccomplishTrue.value?.size ?: 0
            _getPercentById.value = ((trueAccomplishedSize.toDouble() / schedules.size.toDouble())).toFloat()
        }
    }


    fun updateNextMedication(now: LocalTime = LocalTime.now()) {
        viewModelScope.launch {
            val nextMedication = medicationTimes.value?.filter {
                LocalTime.parse(it.scheduledTime).isAfter(now)
            }?.minByOrNull { LocalTime.parse(it.scheduledTime) }

            if (nextMedication != null) {
                _getNextMedication.value = nextMedication
            }
        }
    }
    fun getMedicationsTime(id: Long): Flow<List<MedicationSchedule>>{
        return repositorySchedule.getAllMedicationsScheduleById(id)
    }
    suspend fun deleteAllMedicationHistory() {
        medicationHistoryRepository.deleteAllMedicationHistory()
    }

    private fun searchMedication(query: String): Flow<List<MedicationEntities>> {
        return getAllMedications.map { list ->
            list.filter {
                it.medicationName.contains(query, ignoreCase = true)
            }
        }
    }

    val searchResults: StateFlow<List<MedicationEntities>> = searchQuery
        .debounce(300)
        .flatMapLatest { query ->
            searchMedication(query)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun onSearchQueryChanged(newQuery: String) {
        _searchQuery.value = newQuery
    }

    fun deleteMedications(id: Long) {
        viewModelScope.launch {
            repository.deleteMedication(id = id)
        }
    }
}