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
import com.app.bamboo.data.worker.deleteAllHistoryMedication
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
        deleteAllHistoryMedication(applicationContext)
        setContent {
            val notifyViewModel: NotifyViewModel = hiltViewModel()
            notifyViewModel.showMedicationNotifications(
                this,
                this
            )
            NavControllerComposable()
        }
    }
}

@Composable
fun NavControllerComposable() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val activity = context as Activity
    val notifyViewModel: NotifyViewModel = hiltViewModel()
    notifyViewModel.showMedicationNotifications(activity, context)

    NavHost(navController = navController, startDestination = "mainMedication") {
        composable("mainMedication") {
            val medicationsViewModel: MedicationsViewModel = hiltViewModel()
            medicationsViewModel.getScheduleContainsAccomplishTrue(1)
            medicationsViewModel.getAllMedications()
            medicationsViewModel.percentMedicationsTrue(1)
            MainMedicationComposable(navController)
        }
    }
}

