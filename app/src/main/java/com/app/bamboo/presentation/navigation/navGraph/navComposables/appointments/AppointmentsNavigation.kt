package com.app.bamboo.presentation.navigation.navGraph.navComposables.appointments

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.app.bamboo.presentation.view.appointments.AppointmentsMain
import com.app.bamboo.presentation.view.appointments.insertAppointments.AppointmentTime
import com.app.bamboo.presentation.view.appointments.insertAppointments.AppointmentsOnlineOrOnsite
import com.app.bamboo.presentation.view.appointments.insertAppointments.HospitalAndMedicationName
import com.app.bamboo.presentation.viewModel.appointment.AppointmentsViewModel
import com.app.bamboo.presentation.viewModel.appointment.InsertAppointmentViewModel

fun NavGraphBuilder.AppointmentsNavigation(navController: NavController) {
    composable("appointmentsOnlineOrSite") {
        AppointmentsOnlineOrOnsite(navController)
    }
    composable(
        route = "hospitalAndMedicationName?typeOfAppointment={typeOfAppointment}&onlineOrOnsite={onlineOrOnsite}",
        arguments = listOf(
            navArgument("typeOfAppointment") { type = NavType.StringType; defaultValue = "" },
            navArgument("onlineOrOnsite") { type = NavType.StringType; defaultValue = "" }
        )) {
        val typeOfAppointment = it.arguments?.getString("typeOfAppointment") ?: ""
        val onlineOrOnsite = it.arguments?.getString("onlineOrOnsite") ?: ""

        HospitalAndMedicationName(navController, typeOfAppointment, onlineOrOnsite)
    }
    composable(
        route = "hospitalAndMedicationName?typeOfAppointment={typeOfAppointment}&onlineOrOnsite={onlineOrOnsite}&doctorName={doctorName}&hospitalName={hospitalName}",
        arguments = listOf(
            navArgument("typeOfAppointment") { type = NavType.StringType; defaultValue = "" },
            navArgument("onlineOrOnsite") { type = NavType.StringType; defaultValue = "" },
            navArgument("doctorName") { type = NavType.StringType; defaultValue = "" },
            navArgument("hospitalName") { type = NavType.StringType; defaultValue = "" }
        )) {
        val typeOfAppointment = it.arguments?.getString("typeOfAppointment") ?: ""
        val onlineOrOnsite = it.arguments?.getString("onlineOrOnsite") ?: ""
        val doctorName = it.arguments?.getString("doctorName") ?: ""
        val hospitalName = it.arguments?.getString("hospitalName") ?: ""
        val insertAppointmentViewModel: InsertAppointmentViewModel = hiltViewModel()

        AppointmentTime(
            insertAppointmentViewModel,
            navController,
            typeOfAppointment,
            onlineOrOnsite,
            doctorName,
            hospitalName
        )
    }
    composable("appointments") {
        val appointmentsViewModel: AppointmentsViewModel = hiltViewModel()
        appointmentsViewModel.getAppointmentsById()
        AppointmentsMain(appointmentsViewModel, navController)
    }
}