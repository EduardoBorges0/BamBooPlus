package com.app.bamboo.presentation.viewModel.appointment

import android.view.View
import androidx.lifecycle.ViewModel
import com.app.bamboo.domain.repositories.AppointmentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class InsertAppointmentViewModel @Inject constructor(private val repository: AppointmentRepository) :
    ViewModel() {
}