package com.app.bamboo.di

import com.app.bamboo.domain.use_cases.NextMedicationUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun nextMedicatioUseCase() : NextMedicationUseCase{
        return NextMedicationUseCase()
    }
}