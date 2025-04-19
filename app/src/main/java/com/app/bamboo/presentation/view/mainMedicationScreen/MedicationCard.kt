package com.app.bamboo.presentation.view.mainMedicationScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.bamboo.data.models.medications.MedicationSchedule
import com.app.bamboo.presentation.view.ui.theme.MainColor
import com.app.bamboo.presentation.view.ui.theme.textColor
import com.app.bamboo.presentation.viewModel.medications.MedicationsViewModel

@Composable
fun MedicationCards(
    medicationName: String,
    times: List<MedicationSchedule>,
    modifier: Modifier,
    heightSize: Dp,
    widthSize: Dp
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(31.dp))
            .background(MainColor)
            .height(heightSize / 6)
            .width(widthSize / 1.7f)
    ) {
        Text(
            medicationName,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 30.dp),
            fontSize = 17.sp,
            color = textColor
        )
        Row(modifier = Modifier.align(Alignment.Center)) {
            times.forEach {
                Text(
                    it.scheduledTime,
                    modifier = Modifier
                        .padding(horizontal = 5.dp),
                    fontSize = 17.sp,
                    textDecoration = if (it.accomplish == true) TextDecoration.LineThrough else TextDecoration.None,
                    color = textColor
                )
            }
        }


    }
}
