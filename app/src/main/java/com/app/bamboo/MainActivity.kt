package com.app.bamboo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.app.bamboo.presentation.viewModel.alert.NotifyViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.runtime.livedata.observeAsState
import com.app.bamboo.presentation.viewModel.medications.MedicationsViewModel


@Composable
fun AppNavigation(navController: NavHostController) {
    val notifyViewModel: NotifyViewModel = hiltViewModel()
    val list by notifyViewModel.getBiggerToLower.observeAsState(emptyList())

    Text(if (list.isNotEmpty()) list[0] else "Sem hora")
}


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val notifyViewModel: NotifyViewModel = hiltViewModel()
            val medicationsViewModel: MedicationsViewModel = hiltViewModel()
            val medications = medicationsViewModel.getAllMedications.collectAsState()
            val list by notifyViewModel.getBiggerToLower.observeAsState()
            LaunchedEffect(list) {
                medicationsViewModel.getAllMedications()
                list?.let {
                    if (it.isNotEmpty()) {
                        notifyViewModel.updateMedicationsNotificationTexts(medications.value)
                        notifyViewModel.showNotifications(this@MainActivity)
                        notifyViewModel.moveFirstToLastIfNeeded()
                    }
                }
            }


            val navController = rememberNavController()
            AppNavigation(navController)
        }
    }
}
