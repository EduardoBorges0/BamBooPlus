package com.app.bamboo.medications

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.bamboo.data.models.medications.MedicationSchedule
import com.app.bamboo.domain.repositories.medications.MedicationRepository
import com.app.bamboo.domain.repositories.medications.MedicationScheduleRepository
import com.app.bamboo.presentation.viewModel.medications.InsertMedicationsViewModel
import com.app.bamboo.presentation.viewModel.medications.MedicationsViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate
import java.time.LocalTime
import kotlin.test.assertEquals

class MedicationsUnitTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val medicationRepository = mockk<MedicationRepository>(relaxed = true)
    private val scheduleRepository = mockk<MedicationScheduleRepository>(relaxed = true)

    private lateinit var viewModel: MedicationsViewModel

    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        viewModel = MedicationsViewModel(medicationRepository, scheduleRepository)
    }
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
    @Test
    fun `get next medication if not null`() = runTest {
        //Arrange
        val now = LocalTime.of(10, 0)
        val list = FakeMedications.fakeScheduleMedications
        //Act
        viewModel.medicationTimes.value = list

        viewModel.updateNextMedication(now = now)

        dispatcher.scheduler.advanceUntilIdle()
        //Assert
        val expected = FakeMedications.fakeScheduleMedications[2]
        assertEquals(expected, viewModel.getNextMedication.value)
    }
    @Test
    fun `get next medication if null or empty`() = runTest {
        //Arrange
        val now = LocalTime.of(10, 0)
        val list = listOf<MedicationSchedule>()
        //Act
        viewModel.medicationTimes.value = list

        viewModel.updateNextMedication(now = now)

        dispatcher.scheduler.advanceUntilIdle()
        //Assert
        val expected = null
        assertEquals(expected, viewModel.getNextMedication.value)
    }
}