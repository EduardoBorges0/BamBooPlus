package com.app.bamboo.presentation.view.usefulCompounds

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomButton(onClick: () -> Unit, text: String, modifier: Modifier, color: Color) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(80.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = color
        ),
        shape = RoundedCornerShape(23.dp, 23.dp, 0.dp, 0.dp)
    ) {
        Text(text)
    }
}
