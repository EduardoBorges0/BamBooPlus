package com.app.bamboo.presentation.navigation.navGraph.navComposables

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.app.bamboo.presentation.navigation.navGraph.NavRoutes
import com.app.bamboo.presentation.navigation.navGraph.navComposables.appointments.AppointmentsNavigation
import com.app.bamboo.presentation.navigation.navGraph.navComposables.medications.MedicationNavigations
import com.app.bamboo.presentation.view.appointments.AppointmentsMain
import com.app.bamboo.presentation.view.checkIn.CheckInMain
import com.app.bamboo.presentation.viewModel.alert.NotifyViewModel
import com.app.bamboo.presentation.viewModel.appointment.AppointmentsViewModel
import com.app.bamboo.presentation.viewModel.medications.InsertMedicationsViewModel

@Composable
fun NavHostComposable(navController: NavHostController, isDark: Boolean) {
    val context = LocalContext.current
    val activity = context as Activity
    val notifyViewModel: NotifyViewModel = hiltViewModel()
    val insertMedicationsViewModel: InsertMedicationsViewModel = hiltViewModel()

    notifyViewModel.showMedicationNotifications(activity, context)

    NavHost(navController = navController, startDestination = NavRoutes.MainScreen.route) {
        MedicationNavigations(navController, isDark, insertMedicationsViewModel)
        composable("checkIn") {
            CheckInMain()
        }
        composable("appointments") {
            val appointmentsViewModel: AppointmentsViewModel = hiltViewModel()
            appointmentsViewModel.getAppointmentsById()
            AppointmentsMain(appointmentsViewModel, navController)
        }
        AppointmentsNavigation(navController)
    }
}

