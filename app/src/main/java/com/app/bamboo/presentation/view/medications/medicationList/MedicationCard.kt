package com.app.bamboo.presentation.view.medications.medicationList

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.app.bamboo.R
import com.app.bamboo.data.models.medications.MedicationSchedule
import com.app.bamboo.presentation.navigation.MainNavController
import com.app.bamboo.presentation.view.ui.theme.MainColor
import com.app.bamboo.presentation.view.ui.theme.textColor
import com.app.bamboo.presentation.view.usefulCompounds.LoadingProgressMedication

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MedicationCards(
    navController: NavController,
    medicationName: String,
    id: String,
    times: List<MedicationSchedule>,
    pillOrDrop: String,
    stockQuantity: Int,
    quantityMedication: String,
    modifier: Modifier,
    percent: Float,
    heightSize: Dp,
    widthSize: Dp
) {
    val pillsAndStock =
        if (pillOrDrop == "Pill") "$quantityMedication ${stringResource(R.string.Pills)} | Estoque: $stockQuantity" else "$quantityMedication ${
            "mg"
        } | Estoque: $stockQuantity"
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(31.dp))
            .background(MainColor)
            .height(heightSize / 5.5f)
            .width(widthSize / 1.7f)
            .clickable{
                navController.navigate("medicationDetails?id=$id")
            }
    ) {
        Text(
            medicationName,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = if (times.size > 5) 20.dp else 30.dp),
            fontSize = 17.sp,
            color = textColor
        )
        Text(
            text = pillsAndStock,
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .padding(bottom = if(times.size > 3) 40.dp else 30.dp)
                .align(Alignment.Center),
            fontSize = 17.sp,
            color = textColor
        )
        FlowRow(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(top = if (times.size > 3) 35.dp else 25.dp),
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

        LoadingProgressMedication(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = if (times.size > 3) 20.dp else 40.dp),
            percent = percent
        )
    }
}
