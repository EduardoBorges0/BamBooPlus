package com.app.bamboo.presentation.view.mainScreen.mainMedication.withoutMedication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.traceEventEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.bamboo.presentation.view.ui.theme.MainColor
import com.app.bamboo.presentation.view.ui.theme.textColor

@Composable
fun WithoutMedications(heightSize: Dp, widthSize: Dp, modifier: Modifier, title: String) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(31.dp))
            .size(height = heightSize / 3, width = widthSize / 1.3f)
            .background(
                MaterialTheme.colorScheme.primary
            )
    ) {
        Text(
            title,
            color = textColor,
            modifier = modifier.padding(horizontal = 40.dp),
            fontSize = 15.sp
        )
    }
}