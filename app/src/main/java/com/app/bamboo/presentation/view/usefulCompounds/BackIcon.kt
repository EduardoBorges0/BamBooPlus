package com.app.bamboo.presentation.view.usefulCompounds

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun BackIcon(modifier: Modifier) {
    Icon(
        Icons.AutoMirrored.Filled.KeyboardArrowLeft,
        contentDescription = "back",
        modifier = modifier,
    )
}