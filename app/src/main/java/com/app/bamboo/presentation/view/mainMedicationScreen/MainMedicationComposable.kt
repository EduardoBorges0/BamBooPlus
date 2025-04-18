package com.app.bamboo.presentation.view.mainMedicationScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.app.bamboo.presentation.view.ui.theme.MainColor
import com.app.bamboo.presentation.viewModel.medications.NotifyMedicationsViewModel

@Composable
fun MainMedicationComposable(navController: NavHostController) {
    val notifyMedicationsViewModel: NotifyMedicationsViewModel = hiltViewModel()
    notifyMedicationsViewModel.getMedicationById(1)
    val medicationList = notifyMedicationsViewModel.medication.observeAsState().value?.get(0)
    val heightSize = LocalConfiguration.current.screenHeightDp.dp
    val widthSize = LocalConfiguration.current.screenWidthDp.dp
    Box(modifier = Modifier.fillMaxSize()){
       MedicationCards(modifier = Modifier.align(Alignment.TopCenter), heightSize, widthSize)
    }
}

@Composable
fun MedicationCards(modifier: Modifier, heightSize: Dp, widthSize: Dp){
     Box(
         modifier = modifier.height(heightSize / 6).width(widthSize / 2).background(MainColor)
     ){
         Text("cs")
     }
}