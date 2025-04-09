package com.app.bamboo.presentation.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.app.bamboo.presentation.viewModel.alert.NotifyViewModel
import com.app.bamboo.presentation.viewModel.appointment.InsertAppointmentViewModel
import com.app.bamboo.presentation.viewModel.medications.InsertMedicationsViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.app.bamboo.presentation.viewModel.medications.MedicationsViewModel
import com.app.bamboo.presentation.viewModel.medications.NotifyMedicationsViewModel

@Composable
fun AppNavigation(navController: NavHostController) {
    val notifyViewModel: NotifyViewModel = hiltViewModel()
    val insertMedicationsViewModel: InsertMedicationsViewModel = hiltViewModel()
    val list by notifyViewModel.timeSchedules.observeAsState()
    val id by notifyViewModel.medicationId.observeAsState()
    val medicationName by notifyViewModel.medicationName.observeAsState()
    val insertAppointmentViewModel: InsertAppointmentViewModel = hiltViewModel()
    LaunchedEffect(Unit) {
//        insertAppointmentViewModel.insertAppointment(
//            appointmentType = "Consulta",           // tipo de consulta (ex: "Consulta", "Exame")
//            onlineOrOnSite = "Presencial",          // ou "Online"
//            doctorName = "Dr. João Silva",          // nome do médico
//            hospitalName = "Hospital São Lucas",    // nome do hospital ou clínica
//            appointmentDate = "10/04/2025",         // data no formato dd/MM/yyyy
//            appointmentTime = "14:30",
//        )
    }

    Text(if (list?.isNotEmpty() == true) "Sem hora" else list.toString())
}
