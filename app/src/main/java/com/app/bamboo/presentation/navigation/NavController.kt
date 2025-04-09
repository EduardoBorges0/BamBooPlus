package com.app.bamboo.presentation.navigation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
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
import com.app.bamboo.presentation.view.AppNavigation
import com.app.bamboo.presentation.viewModel.alert.NotifyViewModel
import com.app.bamboo.presentation.viewModel.medications.MedicationsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainNavController : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val notifyViewModel: NotifyViewModel = hiltViewModel()
            val appointmentSummary by notifyViewModel.appointments.observeAsState()
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
    NavHost(navController = navController, startDestination = "mainMedication") {
        composable("mainMedication") {
            val context = LocalContext.current
            val activity = context as Activity

            val notifyViewModel: NotifyViewModel = hiltViewModel()
            val medicationsViewModel: MedicationsViewModel = hiltViewModel()
            val list by notifyViewModel.timeSchedules.observeAsState()

            medicationsViewModel.getAllMedications()

            LaunchedEffect(list) {
                if (!list.isNullOrEmpty()) {
                    notifyViewModel.showMedicationNotifications(context = context, activity = activity)
                }
            }
            AppNavigation(navController)
        }
    }
}
