package com.app.bamboo.presentation.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.app.bamboo.presentation.viewModel.alert.NotifyViewModel
import com.app.bamboo.presentation.viewModel.medications.InsertMedicationsViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.app.bamboo.presentation.viewModel.medications.MedicationsViewModel

@Composable
fun AppNavigation(navController: NavHostController) {
    val notifyViewModel: NotifyViewModel = hiltViewModel()
    val insert: InsertMedicationsViewModel = hiltViewModel()
    LaunchedEffect(Unit) {
//        insert.insertMedication(
//            medicationName = "Dorflex",
//            description = "Para dor",
//            pillOrDrop = "Pill",
//            daysOrHour = "Hour",
//            medicationTime = "10:00",
//            time = 8
//        )
    }
    val list by notifyViewModel.medicationsTime.collectAsState()
    Text(if (list.isNotEmpty()) "Sem hora" else list.toString())
}


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val notifyViewModel: NotifyViewModel = hiltViewModel()
            val medicationsViewModel: MedicationsViewModel = hiltViewModel()
            val list by notifyViewModel.medicationsTime.collectAsState()
            medicationsViewModel.getAllMedications()

            LaunchedEffect(list) {
                list.let {
                    if (it.isNotEmpty()) {
                        notifyViewModel.showNotifications(this@MainActivity, this@MainActivity)
                    }
                }
            }
            val navController = rememberNavController()
            AppNavigation(navController)
        }
    }
}