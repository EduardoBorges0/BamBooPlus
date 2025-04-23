package com.app.bamboo.presentation.view.usefulCompounds

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.app.bamboo.presentation.view.ui.theme.SecondaryColor
import com.app.bamboo.presentation.view.ui.theme.loading
import kotlinx.coroutines.delay

@Composable
fun LoadingProgressMedication(modifier: Modifier, percent: Float) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp)
            .height(4.dp)
    ) {
        Divider(
            color = loading,
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp)
        )

        Divider(
            color = SecondaryColor,
            modifier = Modifier
                .fillMaxWidth(percent)
                .height(5.dp)
        )
    }
}