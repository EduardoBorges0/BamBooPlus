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
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
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

    private val _percentMap = MutableStateFlow<Map<Long, Float>>(emptyMap())
    val percentMap: StateFlow<Map<Long, Float>> = _percentMap


    private val _getNextMedication = MutableStateFlow<List<MedicationSchedule>>(emptyList())
    val getNextMedication: Flow<List<MedicationSchedule>> = _getNextMedication

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    val medicationTimes: Flow<List<MedicationSchedule>> = repositorySchedule.getAllMedicationSchedules()


    fun getAllMedications(): Flow<List<MedicationEntities>>  {
        viewModelScope.launch {
            repository.getAllMedications().collect { medications ->
                _getAllMedications.value = medications
            }
        }
        return getAllMedications
    }

    fun percentMedicationsTrue(id: Long) {
        viewModelScope.launch {
            val schedules = repositorySchedule.getAllMedicationsScheduleById(id).first()
            val trueAccomplishedSize = schedules.count { it.accomplish == true }
            val percent = if (schedules.isNotEmpty()) {
                (trueAccomplishedSize.toDouble() / schedules.size.toDouble()).toFloat()
            } else 0f
            _percentMap.value = _percentMap.value.toMutableMap().apply {
                put(id, percent)
            }
        }
    }

    fun updateNextMedication(currentTime: LocalTime = LocalTime.now()) {
        viewModelScope.launch {
            val formatter = DateTimeFormatter.ofPattern("HH:mm")
            val now = currentTime.format(formatter)
            val nowParsed = LocalTime.parse(now, formatter)

            val upcomingMedications = medicationTimes.first().let { list ->
                val sortedMedications = list.sortedBy {
                    LocalTime.parse(it.scheduledTime)
                }
                val futureToday = sortedMedications.filter {
                    LocalTime.parse(it.scheduledTime).isAfter(nowParsed)
                }

                if (futureToday.isNotEmpty()) {
                    val nextTime = futureToday.minByOrNull {
                        LocalTime.parse(it.scheduledTime)
                    }?.scheduledTime

                    futureToday.filter { it.scheduledTime == nextTime }
                } else {
                    val firstTime = sortedMedications.minByOrNull {
                        LocalTime.parse(it.scheduledTime)
                    }?.scheduledTime

                    sortedMedications.filter { it.scheduledTime == firstTime }
                }
            }
            _getNextMedication.value = upcomingMedications
        }
    }

    fun getTimeUntilNextAlarm(currentTime: LocalTime, scheduledTime: LocalTime): Pair<Long, Long> {
        val today = LocalDate.now()
        var nextAlarm = LocalDateTime.of(today, scheduledTime)
        val now = LocalDateTime.of(today, currentTime)
        if (scheduledTime.isBefore(currentTime) || scheduledTime == currentTime) {
            nextAlarm = nextAlarm.plusDays(1)
        }
        val duration = Duration.between(now, nextAlarm)
        val hours = duration.toHours()
        val minutes = duration.minusHours(hours).toMinutes()
        return Pair(hours, minutes)
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