package com.app.bamboo.presentation.view.medications.medicationList

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.app.bamboo.data.models.medications.MedicationEntities
import com.app.bamboo.presentation.viewModel.medications.MedicationsViewModel

@Composable
fun MedicationList(
    modifier: Modifier,
    searchResults: List<MedicationEntities>,
    medicationsViewModel: MedicationsViewModel,
    heightSize: Dp,
    widthSize: Dp
) {
    LazyRow(modifier = modifier) {
        items(searchResults.size) {
            val percentMap by medicationsViewModel.percentMap.collectAsState()
            val times = medicationsViewModel.getMedicationsTime(it.toLong() + 1)
                .collectAsState(emptyList())
            val id = searchResults[it].id
            val percent = percentMap[id] ?: 0f
            MedicationCards(
                medicationName = searchResults[it].medicationName,
                times = times.value,
                modifier = Modifier
                    .padding(top = heightSize * .26f)
                    .padding(horizontal = 20.dp),
                heightSize = heightSize,
                widthSize = widthSize,
                percent = percent
            )
        }
    }
}