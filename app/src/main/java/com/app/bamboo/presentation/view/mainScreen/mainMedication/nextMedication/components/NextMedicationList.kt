package com.app.bamboo.presentation.view.mainScreen.mainMedication.nextMedication.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.app.bamboo.data.models.medications.MedicationSchedule
import com.app.bamboo.presentation.view.mainScreen.mainMedication.nextMedication.NextMedication
import com.app.bamboo.presentation.viewModel.medications.MedicationsViewModel
import com.app.bamboo.utils.TimeUtils

@Composable
fun NextMedicationList(
    modifier: Modifier,
    medicationsViewModel: MedicationsViewModel,
    nextMedication: List<MedicationSchedule>,
    heightSize: Dp,
    widthSize: Dp
) {
    LazyRow(modifier = modifier) {
        items(nextMedication.size) {
            NextMedication(
                medicationsViewModel = medicationsViewModel,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(top = heightSize * .21f),
                height = heightSize,
                width = widthSize,
                medicationName = nextMedication.get(it).medicationName,
                time = TimeUtils.formattedLocalDateTime(nextMedication.get(it).scheduledTime)
            )
        }
    }
}