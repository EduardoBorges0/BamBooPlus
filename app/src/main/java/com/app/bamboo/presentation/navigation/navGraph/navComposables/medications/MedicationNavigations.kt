package com.app.bamboo.presentation.navigation.navGraph.navComposables.medications

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.app.bamboo.presentation.navigation.navGraph.NavRoutes
import com.app.bamboo.presentation.view.mainScreen.insertMedications.medicationTime.MedicationTime
import com.app.bamboo.presentation.view.mainScreen.insertMedications.medicationsAndStock.MedicationAndStock
import com.app.bamboo.presentation.view.mainScreen.insertMedications.pillOrDrop.PillOrDrop
import com.app.bamboo.presentation.view.mainScreen.mainMedication.MainMedicationComposable
import com.app.bamboo.presentation.view.mainScreen.medicationDetailsScreen.MainMedicationDetailsScreen
import com.app.bamboo.presentation.viewModel.medications.InsertMedicationsViewModel
import com.app.bamboo.presentation.viewModel.medications.MedicationDetailsViewModel
import com.app.bamboo.presentation.viewModel.medications.MedicationsViewModel

fun NavGraphBuilder.MedicationNavigations(
    navController: NavHostController,
    isDark: Boolean,
    insertMedicationsViewModel: InsertMedicationsViewModel
) {
    composable(NavRoutes.MainScreen.route) {
        val medicationsViewModel: MedicationsViewModel = hiltViewModel()
        val nextMedication = medicationsViewModel.getNextMedication.collectAsState(emptyList())
        medicationsViewModel.getAllMedications()
        LaunchedEffect(nextMedication) {
            medicationsViewModel.updateNextMedicationSchedule()
        }
        MainMedicationComposable(navController, medicationsViewModel, isDark)
    }
    composable(
        route = NavRoutes.MedicationDetailScreen.route,
        arguments = listOf(navArgument("id") { type = NavType.StringType; defaultValue = "" })
    ) {
        val medicationDetailsViewModel: MedicationDetailsViewModel = hiltViewModel()
        val id = it.arguments?.getString("id") ?: ""
        NavRoutes.MedicationDetailScreen.createRoute(id.toLong())
        medicationDetailsViewModel.getMedicationsById(id.toLong())
        medicationDetailsViewModel.getMedicationsTime(id.toLong())
        MainMedicationDetailsScreen(
            navController = navController,
            medicationDetailsViewModel = medicationDetailsViewModel
        )
    }
    composable(
        route = "medicationTime",
    ) {
        MedicationTime(
            navController,
            insertMedicationsViewModel
        )
    }
    composable(
        route = "pillOrDrop",
    ) {

        PillOrDrop(
            navController,
            insertMedicationsViewModel = insertMedicationsViewModel,
        )
    }

    composable(
        "medicationAndStock",
    ) {
        MedicationAndStock(navController, insertMedicationsViewModel)
    }
}