package com.app.bamboo.appointments

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.app.bamboo.data.models.AppointmentSummary
import com.app.bamboo.domain.repositories.AppointmentRepository
import com.app.bamboo.presentation.viewModel.appointment.NotifyAppointmentsViewModel
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import kotlin.test.Test

class NotifyAppointmentsUnitTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val repository: AppointmentRepository = mockk()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `updateAccomplishAppointmentIsNull should call updateAccomplish when appointment is expired`() =
        runTest {
            // Arrange
            val expiredAppointment = listOf(
                AppointmentSummary(
                    id = 1,
                    appointmentType = "Consulta",
                    appointmentDate = "20/03/2025",
                    appointmentTime = "13:00"
                ),
                AppointmentSummary(
                    id = 2,
                    appointmentType = "Exame A",
                    appointmentDate = "11/04/2025",
                    appointmentTime = "12:00"
                ),
                AppointmentSummary(
                    id = 3,
                    appointmentType = "Exame B",
                    appointmentDate = "20/06/2025",
                    appointmentTime = "17:00"
                )
            )

            val liveData = MutableLiveData<List<AppointmentSummary>>().apply {
                value = expiredAppointment
            }

            every { repository.getAppointmentSummaries() } returns liveData
            coEvery { repository.updateAccomplish(any(), false) } just Runs

            val viewModel = NotifyAppointmentsViewModel(repository)

            // Act
            viewModel.updateAccomplishAppointmentIsNull()
            advanceUntilIdle()

            // Assert
            coVerify(exactly = 1) { repository.updateAccomplish(1, false) }
        }
}
