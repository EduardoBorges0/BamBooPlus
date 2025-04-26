package com.app.bamboo.presentation.view.mainScreen.medicationDetailsScreen.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.app.bamboo.presentation.view.usefulCompounds.CustomButton

@Composable
fun MedicationDetailsButtons(
    widthSize: Dp,
    heightSize: Dp,
    title: String,
    color: Color,
    onClick: () -> Unit
) {
    CustomButton(
        onClick = { onClick() },
        text = title,
        modifier = Modifier
            .padding(top = 16.dp)
            .width(widthSize / 1.5f)
            .height(heightSize / 12)
            .clip(RoundedCornerShape(16.dp)),
        color = color
    )
}