package com.app.bamboo.presentation.view.medications

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.getValue

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.app.bamboo.presentation.view.utils.SearchTextField
import com.app.bamboo.presentation.viewModel.medications.MedicationsViewModel

@Composable
fun MainMedicationComposable(
    navController: NavHostController,
    medicationsViewModel: MedicationsViewModel
) {
//    val insert: InsertMedicationsViewModel = hiltViewModel()
//    insert.insertMedication(
//        medicationName = "Dorflex",
//        description = "To Pain",
//        pillOrDrop = "Pill",
//        daysOrHour = "Hours",
//        medicationTime = "10:00",
//        date = "2024-08-16",
//        time = 2
//    )
    val heightSize = LocalConfiguration.current.screenHeightDp.dp
    val widthSize = LocalConfiguration.current.screenWidthDp.dp
    val searchQuery by medicationsViewModel.searchQuery.collectAsState()
    val searchResults by medicationsViewModel.searchResults.collectAsState()
    Box(modifier = Modifier.fillMaxSize()) {
        LazyRow(modifier = Modifier.align(Alignment.TopCenter)) {
            items(searchResults.size) {
                medicationsViewModel.getScheduleContainsAccomplishTrue(it.toLong())
                medicationsViewModel.percentMedicationsTrue(it.toLong())
                val percent = medicationsViewModel.getPercentById.collectAsState(0f).value
                val times = medicationsViewModel.getMedicationsTime(it.toLong() + 1)
                    .collectAsState(emptyList())
                MedicationCards(
                    medicationName = searchResults[it].medicationName,
                    times = times.value,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(top = heightSize * .2f)
                        .padding(horizontal = 20.dp),
                    heightSize = heightSize,
                    widthSize = widthSize,
                    percent = percent
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
    }
}
