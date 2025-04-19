package com.app.bamboo.presentation.view.mainMedicationScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.getValue

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.app.bamboo.data.models.medications.MedicationEntities
import com.app.bamboo.presentation.view.ui.theme.MainColor
import com.app.bamboo.presentation.view.utils.SearchTextField
import com.app.bamboo.presentation.viewModel.medications.InsertMedicationsViewModel
import com.app.bamboo.presentation.viewModel.medications.MedicationsViewModel
import com.app.bamboo.presentation.viewModel.medications.NotifyMedicationsViewModel
import java.nio.file.WatchEvent

@Composable
fun MainMedicationComposable(
    navController: NavHostController,
    medicationsViewModel: MedicationsViewModel
) {
//    val notifyMedicationsViewModel: NotifyMedicationsViewModel = hiltViewModel()
    val insert: InsertMedicationsViewModel = hiltViewModel()
//    notifyMedicationsViewModel.getMedicationById(1)
    val heightSize = LocalConfiguration.current.screenHeightDp.dp
    val widthSize = LocalConfiguration.current.screenWidthDp.dp
    val searchQuery by medicationsViewModel.searchQuery.collectAsState()
    val searchResults by medicationsViewModel.searchResults.collectAsState()
    Box(modifier = Modifier.fillMaxSize()) {
        LazyRow(modifier = Modifier.padding(start = 10.dp)) {
            items(searchResults.size) {
                val times = medicationsViewModel.getMedicationsTime(it.toLong() + 1)
                    .collectAsState(emptyList())
                MedicationCards(
                    searchResults[it].medicationName,
                    times.value,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(top = heightSize * .2f)
                        .padding(horizontal = 20.dp),
                    heightSize,
                    widthSize
                )
            }
        }
        SearchTextField(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .width(widthSize / 1.2f),
            value = searchQuery,
            onValueChange = medicationsViewModel::onSearchQueryChanged,
            label = "Buscar Medicamento"
        )
        LoadingProgressMedication(modifier = Modifier.align(Alignment.BottomCenter))
    }
}
