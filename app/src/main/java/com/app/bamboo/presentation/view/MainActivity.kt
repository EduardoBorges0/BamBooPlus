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
import androidx.work.Configuration
import androidx.work.WorkManager
import com.app.bamboo.presentation.viewModel.alert.NotifyViewModel
import com.app.bamboo.presentation.viewModel.medications.InsertMedicationsViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.app.bamboo.presentation.viewModel.medications.MedicationsViewModel
import java.time.LocalTime


@Composable
fun AppNavigation(navController: NavHostController) {
    val notifyViewModel: NotifyViewModel = hiltViewModel()
    val list by notifyViewModel.getBiggerToLower.collectAsState(emptyList())


    Text(if (list.isNotEmpty()) list.toString() else "Sem hora")
}


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val notifyViewModel: NotifyViewModel = hiltViewModel()
            val medicationsViewModel: MedicationsViewModel = hiltViewModel()
            val list by notifyViewModel.getBiggerToLower.collectAsState()
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
