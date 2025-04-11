package com.app.bamboo.appointments

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.app.bamboo.data.models.AppointmentSummary
import com.app.bamboo.domain.repositories.AppointmentRepository
import com.app.bamboo.presentation.viewModel.appointment.NotifyAppointmentsViewModel
import com.app.bamboo.utils.TimeUtils
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
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
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
            val list = FakeAppointments.fakeData

            val expiredAppointments = list.map {
                AppointmentSummary(
                    id = it.id,
                    appointmentType = it.appointmentType,
                    appointmentDate = it.appointmentDate,
                    appointmentTime = it.appointmentTime
                )
            }

            val liveData = MutableLiveData<List<AppointmentSummary>>().apply {
                value = expiredAppointments
            }

            every { repository.getAppointmentSummaries() } returns liveData
            coEvery { repository.updateAccomplish(any(), false) } just Runs

            val viewModel = NotifyAppointmentsViewModel(repository)

            // Act
            viewModel.updateAccomplishAppointmentIsNull()
            advanceUntilIdle()

            // Assert
            coVerify(exactly = 1) { repository.updateAccomplish(1, false) }
            coVerify(exactly = 1) { repository.updateAccomplish(2, false) }
        }
}