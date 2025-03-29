package com.app.bamboo.service

import android.content.Context
import com.app.bamboo.domain.usecases.MoveFirstMedicationIfNeededUseCase
import com.app.bamboo.domain.usecases.ScheduleNextNotificationUseCase
import com.app.bamboo.domain.usecases.SortedListMedicationsUseCase
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class ScheduleAlarmService @Inject constructor(
    private val moveFirstMedicationIfNeededUseCase: MoveFirstMedicationIfNeededUseCase,
    private val scheduleNextNotificationUseCase: ScheduleNextNotificationUseCase,
    private val sortedListMedicationsUseCase: SortedListMedicationsUseCase
) {
    val medicationsTime : StateFlow<List<String>> = moveFirstMedicationIfNeededUseCase.medications

    fun updateMedicationList(medications: List<String>) {
        moveFirstMedicationIfNeededUseCase.updateList(medications)
    }
    fun moveFirstToLastIfNeeded() {
        moveFirstMedicationIfNeededUseCase.moveFirstToLastIfNeeded()
    }
    fun scheduleNextNotification(context: Context, medications: List<String>) {
        scheduleNextNotificationUseCase(context, medications)
    }
    suspend fun getSortedMedications(): List<String> {
        return sortedListMedicationsUseCase.invoke()
    }
}