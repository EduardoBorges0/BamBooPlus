package com.app.bamboo.appointments

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.MutableLiveData
import com.app.bamboo.R
import com.app.bamboo.data.models.AppointmentEntities
import com.app.bamboo.domain.repositories.AppointmentRepository
import com.app.bamboo.presentation.viewModel.appointment.HistoryViewModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HistoryAppointmentUnitTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val repository: AppointmentRepository = mockk()
    private lateinit var viewModel: HistoryViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        viewModel = HistoryViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `appointmentHistory should update isAccomplish with filtered appointments`() = runTest {
        // Arrange


        val liveData = MutableLiveData<List<AppointmentEntities>>()
        liveData.value = FakeAppointments.fakeData

        coEvery { repository.getAllAppointment() } returns liveData

        // Act
        viewModel.appointmentHistory()
        advanceUntilIdle()

        // Assert
        val expected = listOf(
            liveData.value?.get(0),
            liveData.value?.get(1),
        )
        assertEquals(expected, viewModel.isAccomplish.value)
    }

    @Test
    fun `appointmentHistory should update isNullOrEmpty when filterIsAccomplish is null or empty`() = runTest {
        // Arrange
        val fakeData = emptyList<AppointmentEntities>()

        val liveData = MutableLiveData<List<AppointmentEntities>>()
        liveData.value = fakeData

        coEvery { repository.getAllAppointment() } returns liveData

        // Act
        viewModel.appointmentHistory()
        advanceUntilIdle()
        // Assert
        assertEquals(true, viewModel.isNullOrEmpty.value)
    }
}