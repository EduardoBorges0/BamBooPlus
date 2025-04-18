package com.app.bamboo.presentation.view.mainMedicationScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.app.bamboo.presentation.viewModel.medications.NotifyMedicationsViewModel

@Composable
fun MainMedicationComposable(navController: NavHostController) {
    val notifyMedicationsViewModel: NotifyMedicationsViewModel = hiltViewModel()
    notifyMedicationsViewModel.getMedicationById(1)
    val medicationList = notifyMedicationsViewModel.medication.observeAsState().value?.get(0)
    Box(modifier = Modifier.fillMaxSize()){
        Text("$medicationList", modifier = Modifier.align(Alignment.Center))

    }
}
