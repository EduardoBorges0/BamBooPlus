package com.app.bamboo.presentation.view.medications.nextMedication

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.app.bamboo.data.models.medications.MedicationSchedule
import com.app.bamboo.utils.TimeUtils

@Composable
fun NextMedicationList(
    modifier: Modifier,
    nextMedication: List<MedicationSchedule>,
    heightSize: Dp,
    widthSize: Dp
) {
    LazyRow(modifier = modifier) {
        items(nextMedication.size) {
            NextMedication(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(top = heightSize * .21f),
                heightSize,
                widthSize,
                nextMedication.get(it).medicationName,
                TimeUtils.formattedLocalDateTime(nextMedication.get(it).scheduledTime)
            )
        }
    }
}