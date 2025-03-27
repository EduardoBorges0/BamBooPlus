package com.app.bamboo.presentation.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
    val list by notifyViewModel.getBiggerToLower.collectAsState(emptyList())
    val medicationsViewModel: InsertMedicationsViewModel = hiltViewModel()
    Text(if (list.isNotEmpty()) list[0] else "Sem hora")
}


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val notifyViewModel: NotifyViewModel = hiltViewModel()
            val medicationsViewModel: MedicationsViewModel = hiltViewModel()
            val list by notifyViewModel.getBiggerToLower.collectAsState()
            LaunchedEffect(list) {
                medicationsViewModel.getAllMedications()
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
