package com.app.bamboo.presentation.view.medications.medicationList

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.bamboo.data.models.medications.MedicationEntities
import com.app.bamboo.presentation.viewModel.medications.MedicationsViewModel
import kotlinx.coroutines.flow.first

@Composable
fun MedicationList(
    navController: NavController,
    modifier: Modifier,
    searchResults: List<MedicationEntities>,
    medicationsViewModel: MedicationsViewModel,
    heightSize: Dp,
    widthSize: Dp
) {
    LazyRow(modifier = modifier) {
        items(searchResults.size) {
            val percentMap by medicationsViewModel.percentMap.collectAsState()
            val id = searchResults[it].id
            val times = medicationsViewModel.getMedicationsTime(id)
                .collectAsState(emptyList())
            val percent = percentMap[id] ?: 0f
            MedicationCards(
                navController = navController,
                medicationName = searchResults[it].medicationName,
                times = times.value,
                modifier = Modifier
                    .padding(top = heightSize * .26f)
                    .padding(horizontal = 20.dp),
                heightSize = heightSize,
                widthSize = widthSize,
                stockQuantity = searchResults[it].quantity,
                pillOrDrop = searchResults[it].pillOrDrop,
                quantityMedication = searchResults[it].amountMedication.toString(),
                percent = percent,
                id = id.toString()
            )
        }
    }
}