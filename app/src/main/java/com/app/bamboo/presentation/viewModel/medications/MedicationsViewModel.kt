package com.app.bamboo.presentation.viewModel.medications

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.bamboo.data.models.medications.MedicationEntities
import com.app.bamboo.data.models.medications.MedicationSchedule
import com.app.bamboo.domain.repositories.medications.MedicationHistoryRepository
import com.app.bamboo.domain.repositories.medications.MedicationRepository
import com.app.bamboo.domain.repositories.medications.MedicationScheduleRepository
import com.app.bamboo.utils.TimeUtils.parseToLocalDateTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
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

    private val _percentMap = MutableStateFlow<Map<Long, Float>>(emptyMap())
    val percentMap: StateFlow<Map<Long, Float>> = _percentMap

    private val _getNextMedication = MutableStateFlow<List<MedicationSchedule>>(emptyList())
    val getNextMedication: Flow<List<MedicationSchedule>> = _getNextMedication

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _medicationScheduleTimes = MutableStateFlow<List<MedicationSchedule>>(emptyList())
    val medicationScheduleTimes: StateFlow<List<MedicationSchedule>> = _medicationScheduleTimes

    init {
        viewModelScope.launch {
            repositorySchedule.getAllMedicationSchedules().collect {
                updateNextMedicationSchedule()
            }
        }
    }

    fun getAllMedications(): Flow<List<MedicationEntities>> {
        viewModelScope.launch {
            repository.getAllMedications().collect { medications ->
                _getAllMedications.value = medications
            }
        }
        return getAllMedications
    }
    fun calculateTrueMedicationPercentage(id: Long) {
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

    fun updateNextMedicationSchedule(currentDateTime: LocalDateTime = LocalDateTime.now()) {
        viewModelScope.launch {
            _medicationScheduleTimes.value = repositorySchedule.getAllMedicationSchedules().first()
            val upcomingMedications = medicationScheduleTimes.first().let { list ->
                val scheduledDateTimes = list.map {
                    val fullDateTime = parseToLocalDateTime(it.date, it.scheduledTime)
                    it to fullDateTime
                }
                val futureMedications = scheduledDateTimes.filter { (_, dateTime) ->
                    dateTime.isAfter(currentDateTime)
                }
                if (futureMedications.isNotEmpty()) {
                    val nextTime = futureMedications.minByOrNull { it.second }?.second
                    futureMedications.filter { it.second == nextTime }.map { it.first }
                } else {
                    val earliestTime = scheduledDateTimes.minByOrNull { it.second }?.second
                    scheduledDateTimes.filter { it.second == earliestTime }.map { it.first }
                }
            }
            _getNextMedication.value = upcomingMedications
        }
    }

    fun getTimeUntilNextAlarm(currentTime: LocalTime, scheduledTime: LocalTime, date: LocalDate): Pair<Long, Long> {
        val today = LocalDate.now()
        var nextAlarm = LocalDateTime.of(today, scheduledTime)
        val now = LocalDateTime.of(today, currentTime)
        if (scheduledTime.isBefore(currentTime) || scheduledTime == currentTime) {
            nextAlarm = LocalDateTime.of(date, scheduledTime)
        }
        val duration = Duration.between(now, nextAlarm)
        val hours = duration.toHours()
        val minutes = duration.minusHours(hours).toMinutes()
        return Pair(hours, minutes)
    }

    fun getMedicationsTime(id: Long): Flow<List<MedicationSchedule>> {
        return repositorySchedule.getAllMedicationsScheduleById(id)
    }

    suspend fun deleteAllMedicationHistory() {
        medicationHistoryRepository.deleteAllMedicationHistory()
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val searchResults: StateFlow<List<MedicationEntities>> = searchQuery
        .debounce(300)
        .flatMapLatest { query ->
            searchMedication(query)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun onSearchQueryChanged(newQuery: String) {
        _searchQuery.value = newQuery
    }

    private fun searchMedication(query: String): Flow<List<MedicationEntities>> {
        return getAllMedications.map { list ->
            list.filter {
                it.medicationName.contains(query, ignoreCase = true)
            }
        }
    }
}