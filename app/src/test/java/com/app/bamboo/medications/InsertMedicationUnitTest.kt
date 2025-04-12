package com.app.bamboo.medications

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
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
    fun `insertMedication with Days should insert medication correctly`() = runTest {
        // Arrange
        val medication = FakeMedications.fakeMedications[1]
        val expectedId = 1L
        val daysInHours = medication.time * 24

        coEvery {
            medicationRepository.insertMedication(
                medicationName = medication.medicationName,
                description = medication.description,
                pillOrDrop = medication.pillOrDrop,
                daysOrHour = medication.daysOrHours,
                medicationTime = medication.medicationTime,
                time = daysInHours
            )
        } returns expectedId

        coEvery { scheduleRepository.insertSchedule(any()) } returns Unit

        // Act
        viewModel.insertMedication(
            medicationName = medication.medicationName,
            description = medication.description,
            pillOrDrop = medication.pillOrDrop,
            daysOrHour = medication.daysOrHours,
            medicationTime = medication.medicationTime,
            time = medication.time
        )

        advanceUntilIdle()

        // Assert
        coVerify(exactly = 1) {
            medicationRepository.insertMedication(
                medicationName = medication.medicationName,
                description = medication.description,
                pillOrDrop = medication.pillOrDrop,
                daysOrHour = medication.daysOrHours,
                medicationTime = medication.medicationTime,
                time = daysInHours
            )
        }
    }
    @Test
    fun `insertMedication with Hours should insert medication correctly`() = runTest {
        // Arrange
        val medication = FakeMedications.fakeMedications[0]
        val expectedId = 1L
        val daysInHours = medication.time

        coEvery {
            medicationRepository.insertMedication(
                medicationName = medication.medicationName,
                description = medication.description,
                pillOrDrop = medication.pillOrDrop,
                daysOrHour = medication.daysOrHours,
                medicationTime = medication.medicationTime,
                time = daysInHours
            )
        } returns expectedId

        coEvery { scheduleRepository.insertSchedule(any()) } returns Unit

        // Act
        viewModel.insertMedication(
            medicationName = medication.medicationName,
            description = medication.description,
            pillOrDrop = medication.pillOrDrop,
            daysOrHour = medication.daysOrHours,
            medicationTime = medication.medicationTime,
            time = medication.time
        )

        advanceUntilIdle()

        // Assert
        coVerify(exactly = 1) {
            medicationRepository.insertMedication(
                medicationName = medication.medicationName,
                description = medication.description,
                pillOrDrop = medication.pillOrDrop,
                daysOrHour = medication.daysOrHours,
                medicationTime = medication.medicationTime,
                time = daysInHours
            )
        }
    }
    @Test
    fun `generateSchedules with Hours should return correct number of schedules`() = runTest {
        val medications = FakeMedications.fakeMedications[0]

        val result = viewModel.insertSchedules(
            medicationId = medications.id,
            daysOrHour = medications.daysOrHours,
            startTime = medications.medicationTime,
            intervalHours = medications.time.toInt(),
            medicationName = medications.medicationName
        )

        assertEquals(3, result.size)
        assertEquals("10:00", result[0].scheduledTime)
        assertEquals("18:00", result[1].scheduledTime)
        assertEquals("02:00", result[2].scheduledTime)
    }
    @Test
    fun `generateSchedules with Days should return correct number of schedules`() = runTest {
        val medications = FakeMedications.fakeMedications[1]

        val result = viewModel.insertSchedules(
            medicationId = medications.id,
            daysOrHour = medications.daysOrHours,
            startTime = medications.medicationTime,
            intervalHours = medications.time.toInt(),
            medicationName = medications.medicationName
        )

        assertEquals(2, result.size)
        assertEquals("10:00", result[0].scheduledTime)
        assertEquals("10:00", result[1].scheduledTime)
    }
}