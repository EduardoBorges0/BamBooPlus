package com.app.bamboo.presentation.navigation

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavArgument
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.app.bamboo.data.worker.quantityThreshold.alertQuantityThreshold
import com.app.bamboo.domain.alarmManager.background_tasks.everyScheduleFalse
import com.app.bamboo.presentation.view.appointments.AppointmentsMain
import com.app.bamboo.presentation.view.checkIn.CheckInMain
import com.app.bamboo.presentation.view.insertMedications.medicationsAndStock.MedicationAndStock
import com.app.bamboo.presentation.view.insertMedications.PillOrDrop
import com.app.bamboo.presentation.view.insertMedications.medicationTime.MedicationTime
import com.app.bamboo.presentation.view.medications.MainMedicationComposable
import com.app.bamboo.presentation.view.medications.medicationDetailsScreen.MainMedicationDetailsScreen
import com.app.bamboo.presentation.viewModel.alert.NotifyViewModel
import com.app.bamboo.presentation.viewModel.medications.InsertMedicationsViewModel
import com.app.bamboo.presentation.viewModel.medications.MedicationDetailsViewModel
import com.app.bamboo.presentation.viewModel.medications.MedicationsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainNavController : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            alertQuantityThreshold(applicationContext)
            everyScheduleFalse(applicationContext)
            val navController = rememberNavController()
            val notifyViewModel: NotifyViewModel = hiltViewModel()
            notifyViewModel.showMedicationNotifications(this, this)

            val navBackStackEntry = navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry.value?.destination?.route

            Scaffold(
                bottomBar = {
                    if (currentRoute in listOf("main", "appointments", "checkIn")) {
                        BottomNavigationBar(navController)
                    }
                }
            ) {
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
            val nextMedication = medicationsViewModel.getNextMedication.collectAsState(emptyList())
            medicationsViewModel.getAllMedications()
            LaunchedEffect(nextMedication) {
                medicationsViewModel.updateNextMedicationSchedule()
            }
            MainMedicationComposable(navController, medicationsViewModel)
        }
        composable(
            route = "medicationTime?medicationName={medicationName}&quantity={quantity}&description={description}&quantityThreshold={quantityThreshold}",
            arguments = listOf(
                navArgument("medicationName") { type = NavType.StringType; defaultValue = "" },
                navArgument("quantity") { type = NavType.StringType; defaultValue = "" },
                navArgument("quantityThreshold") { type = NavType.StringType; defaultValue = "" },
                navArgument("description") { type = NavType.StringType; defaultValue = "" }
            )
        ) {
            val medicationName = it.arguments?.getString("medicationName") ?: ""
            val quantityThreshold = it.arguments?.getString("quantityThreshold") ?: ""
            val quantity = it.arguments?.getString("quantity") ?: ""
            val description = it.arguments?.getString("description") ?: ""

            MedicationTime(navController, medicationName, quantity, description, quantityThreshold)
        }
        composable(
            route = "pillOrDrop?medicationName={medicationName}&quantity={quantity}&firstTime={firstTime}&selectedDate={selectedDate}&hoursOrDays={hoursOrDays}&intervalTime={intervalTime}&description={description}&quantityThreshold={quantityThreshold}",
            arguments = listOf(
                navArgument("description") { type = NavType.StringType; defaultValue = "" },
                navArgument("medicationName") { type = NavType.StringType; defaultValue = "" },
                navArgument("quantity") { type = NavType.StringType; defaultValue = "" },
                navArgument("quantityThreshold") { type = NavType.StringType; defaultValue = "" },
                navArgument("firstTime") { type = NavType.StringType; defaultValue = "" },
                navArgument("selectedDate") { type = NavType.StringType; defaultValue = "" },
                navArgument("hoursOrDays") { type = NavType.StringType; defaultValue = "" },
                navArgument("intervalTime") { type = NavType.StringType; defaultValue = "" }
            )
        ) {
            val insertMedicationsViewModel: InsertMedicationsViewModel = hiltViewModel()
            val medicationName = it.arguments?.getString("medicationName") ?: ""
            val description = it.arguments?.getString("description") ?: ""
            val quantityThreshold = it.arguments?.getString("quantityThreshold") ?: ""
            val quantity = it.arguments?.getString("quantity") ?: ""
            val firstTime = it.arguments?.getString("firstTime") ?: ""
            val selectedDate = it.arguments?.getString("selectedDate") ?: ""
            val hoursOrDays = it.arguments?.getString("hoursOrDays") ?: ""
            val intervalTime = it.arguments?.getString("intervalTime") ?: ""

            PillOrDrop(
                navController,
                description,
                insertMedicationsViewModel = insertMedicationsViewModel,
                medicationName,
                quantity,
                quantityThreshold,
                firstTime,
                selectedDate,
                hoursOrDays,
                intervalTime
            )
        }

        composable(
            "medicationAndStock",
            ) {
            MedicationAndStock(navController)
        }
        composable(
            route = "medicationDetails?id={id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType; defaultValue = "" })
        ) {
            val medicationDetailsViewModel: MedicationDetailsViewModel = hiltViewModel()
            val id = it.arguments?.getString("id") ?: ""
            medicationDetailsViewModel.getMedicationsById(id.toLong())
            medicationDetailsViewModel.getMedicationsTime(id.toLong())
            MainMedicationDetailsScreen(navController, medicationDetailsViewModel)
        }
        composable("checkIn") {
            CheckInMain()
        }
        composable("appointments") {
            AppointmentsMain()
        }
    }
}

