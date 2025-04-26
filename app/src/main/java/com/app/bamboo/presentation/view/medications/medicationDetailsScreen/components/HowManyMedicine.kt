package com.app.bamboo.presentation.view.medications.medicationDetailsScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.bamboo.data.models.medications.MedicationSchedule
import com.app.bamboo.presentation.view.ui.theme.MainColor
import com.app.bamboo.presentation.view.ui.theme.textColor

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HowManyMedicine(
    medicationName: String,
    description: String,
    times: List<MedicationSchedule>,
    widthSize: Dp
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(31.dp))
            .wrapContentHeight()
            .width(widthSize / 1.5f)
            .background(MainColor)
            .padding(46.dp)
    ) {
        Text(
            medicationName,
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            fontSize = 20.sp,
            color = textColor
        )
        Text(
            description,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 5.dp),
            fontSize = 18.sp,
            color = textColor
        )
        FlowRow(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 15.dp),
            maxItemsInEachRow = 4,
            maxLines = 2
        ) {
            times.forEach {
                Text(
                    text = it.scheduledTime,
                    modifier = Modifier.padding(horizontal = 5.dp),
                    fontSize = 17.sp,
                    textDecoration = if (it.accomplish == true) TextDecoration.LineThrough else TextDecoration.None,
                    color = textColor
                )
            }
        }
    }
}