package com.app.bamboo.presentation.navigation

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
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
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        deleteAllHistoryMedication(applicationContext)
        setContent {
            val notifyViewModel: NotifyViewModel = hiltViewModel()
            val navController = rememberNavController()

            notifyViewModel.showMedicationNotifications(
                this,
                this
            )
            Scaffold(bottomBar = {
                BottomNavigationBar(navController)

            }) {
                NavControllerComposable(navController)
            }
        }
    }
}

@Composable
fun NavControllerComposable(navController: NavHostController) {
    val context = LocalContext.current
    val activity = context as Activity
    val notifyViewModel: NotifyViewModel = hiltViewModel()
    notifyViewModel.showMedicationNotifications(activity, context)

    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            val medicationsViewModel: MedicationsViewModel = hiltViewModel()
            medicationsViewModel.getScheduleContainsAccomplishTrue(1)
            medicationsViewModel.getAllMedications()
            medicationsViewModel.percentMedicationsTrue(1)
            MainMedicationComposable(navController)
        }
    }
}

