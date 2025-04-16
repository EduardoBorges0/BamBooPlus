package com.app.bamboo.presentation.navigation

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.bamboo.presentation.view.mainMedicationScreen.MainMedicationComposable
import com.app.bamboo.presentation.viewModel.alert.NotifyViewModel
import com.app.bamboo.presentation.viewModel.appointment.NotifyAppointmentsViewModel
import com.app.bamboo.presentation.viewModel.medications.InsertMedicationsViewModel
import com.app.bamboo.presentation.viewModel.medications.MedicationsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainNavController : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val notifyViewModel: NotifyViewModel = hiltViewModel()
            val insert : InsertMedicationsViewModel = hiltViewModel()
            LaunchedEffect(Unit) {
//               insert.insertMedication(
//                   medicationName = "Dorflex",
//                   description = "Para dor",
//                   pillOrDrop = "Pill",
//                   daysOrHour = "Days",
//                   medicationTime = "12:00",
//                   date = "2025-04-16",
//                   time = 4
//               )
            }

            val appointmentSummary by notifyViewModel.appointments.observeAsState()
            notifyViewModel.showMedicationNotifications(this@MainNavController, this@MainNavController)

            LaunchedEffect(appointmentSummary) {
                notifyViewModel.showAppointmentNotification(this@MainNavController)
            }
            NavControllerComposable()
        }
    }
}
@Composable
fun NavControllerComposable() {
    val navController = rememberNavController()
    val notifyViewModel: NotifyViewModel = hiltViewModel()
    val context = LocalContext.current
    val activity = context as Activity

    // Observar as mudanças nas listas
    val medications = notifyViewModel.getAllMedications.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        notifyViewModel.showMedicationNotifications(activity, context)
    }

    // Garanta que o log seja chamado sempre que a lista for observada
    medications.value.forEach { medication ->
        Log.d("ALARME", "Medicamento: ${medication.medicationName}")
    }

    NavHost(navController = navController, startDestination = "mainMedication") {
        composable("mainMedication") {
            MainMedicationComposable(navController)
        }
    }
}

