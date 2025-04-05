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
    LaunchedEffect(Unit) {
//            insertMedicationsViewModel.insertMedication(
//        medicationName = "Dorflex",
//        description = "Para dor",
//        pillOrDrop = "Pill",
//        daysOrHour = "Hour",
//        medicationTime = "10:00",
//        time = 8
//    )
    }

    Text(if (list?.isNotEmpty() == true) "Sem hora" else list.toString())
}


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val notifyViewModel: NotifyViewModel = hiltViewModel()
            val medicationsViewModel: MedicationsViewModel = hiltViewModel()
            val list by notifyViewModel.timeSchedules.observeAsState()
            medicationsViewModel.getAllMedications()
            val notifyMedicationsViewModel: NotifyMedicationsViewModel = hiltViewModel()
            val times by notifyMedicationsViewModel.medicationTimes.observeAsState(emptyList())

            LaunchedEffect(times) {
                if (times.isNotEmpty()) {
                    notifyMedicationsViewModel.getLastTime(times)
                }
            }
            LaunchedEffect(list) {
                list.let {
                    if (it != null) {
                        if (it.isNotEmpty()) {
                            notifyViewModel.showNotifications(this@MainActivity, this@MainActivity)
                        }
                    }
                }
            }
            val navController = rememberNavController()
            AppNavigation(navController)
        }
    }
}