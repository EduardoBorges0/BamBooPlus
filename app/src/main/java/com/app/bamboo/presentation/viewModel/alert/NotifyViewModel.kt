package com.app.bamboo.presentation.viewModel.alert

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.bamboo.data.models.MedicationEntities
import com.app.bamboo.domain.usecases.GetSortedMedicationsUseCase
import com.app.bamboo.domain.usecases.MoveFirstMedicationIfNeededUseCase
import com.app.bamboo.domain.usecases.ScheduleNextNotificationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotifyViewModel @Inject constructor(
    private val getSortedMedicationsUseCase: GetSortedMedicationsUseCase,
    private val moveFirstMedicationIfNeededUseCase: MoveFirstMedicationIfNeededUseCase,
    private val scheduleNextNotificationUseCase: ScheduleNextNotificationUseCase
) : ViewModel() {
    val getBiggerToLower: LiveData<List<String>> = moveFirstMedicationIfNeededUseCase.medications

    private val _title = MutableLiveData("Lembrete Diário")
    val title: LiveData<String> = _title

    private val _message = MutableLiveData("Está na hora de conferir seu app!")
    val message: LiveData<String> = _message

    init {
        updateMedicationList()
        startAutoUpdate()
    }

    fun updateMedicationList() {
        viewModelScope.launch {
            val sortedList = getSortedMedicationsUseCase()
            moveFirstMedicationIfNeededUseCase.updateList(sortedList)
        }
    }

    fun moveFirstToLastIfNeeded() {
        viewModelScope.launch {
            moveFirstMedicationIfNeededUseCase.moveFirstToLastIfNeeded()
        }
    }

    private fun startAutoUpdate() {
        viewModelScope.launch {
            while (true) {
                delay(6000)
                moveFirstToLastIfNeeded()
            }
        }
    }

    fun showNotifications(context: Context) {
        viewModelScope.launch {
            getBiggerToLower.value?.let {
                scheduleNextNotificationUseCase(context, it, title.value, message.value)
            }
        }
    }

    suspend fun updateMedicationsNotificationTexts(medications: List<MedicationEntities>) {
        if(medications.isNotEmpty()){
            val medication = medications.filter {
                it.medicationTime == (getBiggerToLower.value?.get(0) ?: "")
            }
            val name = medication.map {
                it.medicationName
            }
            val time = medication.map {
                it.medicationTime
            }
            _title.value = "Hora do ${name[0]}"
            _message.value = "Já são ${time[0]}, Boo está te esperando"
        }
    }
}
