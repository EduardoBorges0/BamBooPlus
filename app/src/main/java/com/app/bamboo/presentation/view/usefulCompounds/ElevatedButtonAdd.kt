package com.app.bamboo.presentation.view.usefulCompounds

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.app.bamboo.presentation.view.ui.theme.SecondaryColor
import com.app.bamboo.presentation.view.ui.theme.textColor

@Composable
fun ElevatedButtonAdd(modifier: Modifier, action: () -> Unit, heightSize: Dp){
    ElevatedButton(
        onClick = {
            action()
        },
        modifier = modifier
            .padding(bottom = heightSize / 6)
            .padding(end = 15.dp)
            .height(heightSize / 12),
        shape = RoundedCornerShape(26.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = SecondaryColor
        )
    ) {
        Icon(Icons.Filled.Add, contentDescription = "add", tint = textColor)
    }
}