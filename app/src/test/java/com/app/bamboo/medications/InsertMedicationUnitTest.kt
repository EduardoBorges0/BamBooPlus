package com.app.bamboo.medications

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.app.bamboo.data.models.MedicationSchedule
import com.app.bamboo.domain.repositories.MedicationRepository
import com.app.bamboo.domain.repositories.MedicationScheduleRepository
import com.app.bamboo.presentation.viewModel.medications.InsertMedicationsViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class InsertMedicationsViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val medicationRepository = mockk<MedicationRepository>(relaxed = true)
    private val scheduleRepository = mockk<MedicationScheduleRepository>(relaxed = true)

    private lateinit var viewModel: InsertMedicationsViewModel

    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        viewModel = InsertMedicationsViewModel(medicationRepository, scheduleRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `insertMedication with Hours should insert medication and schedules correctly`() = runTest {
        // Arrange
        val medicationName = "Paracetamol"
        val description = "Para dor"
        val pillOrDrop = "Pill"
        val daysOrHour = "Hours"
        val medicationTime = "08:00"
        val intervalHours = 6L
        val expectedMedicationId = 1L

        coEvery {
            medicationRepository.insertMedication(
                any(), any(), any(), any(), any(), any()
            )
        } returns expectedMedicationId

        // Act
        viewModel.insertMedication(
            medicationName = medicationName,
            description = description,
            pillOrDrop = pillOrDrop,
            daysOrHour = daysOrHour,
            medicationTime = medicationTime,
            time = intervalHours
        )

        advanceUntilIdle() // Aguarda coroutines

        // Assert
        coVerify(exactly = 1) {
            medicationRepository.insertMedication(
                medicationName,
                description,
                pillOrDrop,
                daysOrHour,
                medicationTime,
                intervalHours
            )
        }

        coVerify(exactly = 1) {
            scheduleRepository.insertSchedule(match {
                it.size == 4 &&
                        it[0].scheduledTime.startsWith("08") &&
                        it[1].scheduledTime.startsWith("14") &&
                        it[2].scheduledTime.startsWith("20") &&
                        it[3].scheduledTime.startsWith("02")
            })
        }
    }
}
