package com.app.bamboo.presentation.view.mainMedicationScreen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.app.bamboo.presentation.viewModel.alert.NotifyViewModel
import com.app.bamboo.presentation.viewModel.appointment.InsertAppointmentViewModel
import com.app.bamboo.presentation.viewModel.medications.InsertMedicationsViewModel

@Composable
fun AppNavigation(navController: NavHostController) {
    val notifyViewModel: NotifyViewModel = hiltViewModel()
    val insertMedicationsViewModel: InsertMedicationsViewModel = hiltViewModel()
    val list by notifyViewModel.timeSchedules.observeAsState()
    val id by notifyViewModel.medicationId.observeAsState()
    val medicationName by notifyViewModel.medicationName.observeAsState()
    val insertAppointmentViewModel: InsertAppointmentViewModel = hiltViewModel()

    Text(if (list?.isNotEmpty() == true) "Sem hora" else list.toString())
}
