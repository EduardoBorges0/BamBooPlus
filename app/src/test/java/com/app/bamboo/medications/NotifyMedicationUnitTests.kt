package com.app.bamboo.medications

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.app.bamboo.data.models.medications.MedicationSchedule
import com.app.bamboo.domain.repositories.appointments.AppointmentRepository
import com.app.bamboo.domain.repositories.medications.MedicationHistoryRepository
import com.app.bamboo.domain.repositories.medications.MedicationRepository
import com.app.bamboo.domain.repositories.medications.MedicationScheduleRepository
import com.app.bamboo.presentation.viewModel.appointment.HistoryViewModel
import com.app.bamboo.presentation.viewModel.medications.NotifyMedicationsViewModel
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule

class NotifyMedicationUnitTests {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val medicationRepository: MedicationRepository = mockk()
    private val scheduleRepository: MedicationScheduleRepository = mockk()
    private val medicationHistoryRepository: MedicationHistoryRepository = mockk()


    private lateinit var viewModel: NotifyMedicationsViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        viewModel = NotifyMedicationsViewModel(scheduleRepository, medicationRepository, medicationHistoryRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

}