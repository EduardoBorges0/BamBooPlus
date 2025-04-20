package com.app.bamboo.presentation.navigation

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.bamboo.data.worker.deleteMedicationHistory.deleteAllHistoryMedication
import com.app.bamboo.domain.alarmManager.worker.scheduleDailyCleanupAlarm
import com.app.bamboo.presentation.view.appointments.AppointmentsMain
import com.app.bamboo.presentation.view.checkIn.CheckInMain
import com.app.bamboo.presentation.view.medications.MainMedicationComposable
import com.app.bamboo.presentation.viewModel.alert.NotifyViewModel
import com.app.bamboo.presentation.viewModel.medications.MedicationsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainNavController : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        deleteAllHistoryMedication(applicationContext)
        scheduleDailyCleanupAlarm(this)
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
            medicationsViewModel.getAllMedications()
            MainMedicationComposable(navController, medicationsViewModel)
        }
        composable("checkIn"){
            CheckInMain()
        }
        composable("appointments") {
            AppointmentsMain()
        }
    }
}

