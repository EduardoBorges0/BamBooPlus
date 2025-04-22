package com.app.bamboo.presentation.view.usefulCompounds

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun BackIcon(navController: NavController) {
    Icon(
        Icons.AutoMirrored.Filled.KeyboardArrowLeft,
        contentDescription = "back",
        modifier = Modifier
            .padding(39.dp)
            .padding(top = 25.dp)
            .clickable {
                navController.popBackStack()
            },
    )
}