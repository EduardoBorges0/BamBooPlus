package com.app.bamboo.di

import com.app.bamboo.domain.repositories.MedicationRepository
import com.app.bamboo.domain.usecases.GetSortedMedicationsUseCase
import com.app.bamboo.domain.usecases.MoveFirstMedicationIfNeededUseCase
import com.app.bamboo.domain.usecases.ScheduleNextNotificationUseCase
import com.app.bamboo.domain.usecases.UpdateNotificationName
import dagger.Module
import dagger.Provides
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface NotificationUseCasesEntryPoint {
    fun getSortedMedicationsUseCase(): GetSortedMedicationsUseCase
    fun moveFirstMedicationIfNeededUseCase(): MoveFirstMedicationIfNeededUseCase
    fun scheduleNextNotificationUseCase(): ScheduleNextNotificationUseCase
    fun updateNotificationName(): UpdateNotificationName
}
