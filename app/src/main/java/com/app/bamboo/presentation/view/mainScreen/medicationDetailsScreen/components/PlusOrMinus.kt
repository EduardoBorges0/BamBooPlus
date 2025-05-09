package com.app.bamboo.presentation.view.mainScreen.medicationDetailsScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.bamboo.presentation.view.ui.theme.MainColor
import com.app.bamboo.presentation.view.ui.theme.textColor

@Composable
fun PlusOrMinus(
    title: String, value: Int, heightSize: Dp, widthSize: Dp, onQuantityChange: (Int) -> Unit,
) {
    Box(
        modifier = Modifier
            .padding(top = 15.dp)
            .clip(RoundedCornerShape(31.dp))
            .width(widthSize / 1.5f)
            .height(heightSize / 8)
            .background(MaterialTheme.colorScheme.primary)

    ) {
        Text(
            title,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 20.dp),
            color = Color.White
        )
        Text(
            value.toString(),
            modifier = Modifier
                .padding(top = 20.dp)
                .align(Alignment.Center),
            color = Color.White
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterStart)
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                onQuantityChange(value + 1)
            }) {
                Text("+", color = textColor, fontSize = 25.sp)
            }
            IconButton(onClick = {
                if (value > 0) onQuantityChange(value - 1)

            }) {
                Text("-", color = textColor, fontSize = 25.sp)
            }
        }
    }
}
