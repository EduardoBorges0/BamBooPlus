package com.app.bamboo.presentation.view.medications.medicationDetailsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.bamboo.presentation.view.ui.theme.MainColor
import com.app.bamboo.presentation.view.ui.theme.textColor

@Composable
fun PlusOrMinus(title: String, value: Int) {
    val widthSize = LocalConfiguration.current.screenWidthDp.dp
    val heightSize = LocalConfiguration.current.screenHeightDp.dp

    var quantity by remember { mutableStateOf(value) }

    Box(
        modifier = Modifier
            .padding(top = 15.dp)
            .clip(RoundedCornerShape(31.dp))
            .width(widthSize / 1.5f)
            .height(heightSize / 8)
            .background(MainColor)
    ) {
        Text(
            title,
            modifier = Modifier.align(Alignment.TopCenter).padding(top = 20.dp),
            color = Color.White
        )
        Text(
            quantity.toString(),
            modifier = Modifier.padding(top = 20.dp).align(Alignment.Center),
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
                quantity += 1
            }) {
                Text("+", color = textColor, fontSize = 25.sp)
            }
            IconButton(onClick = {
                if (quantity > 0) quantity -= 1
            }) {
                Text("-", color = textColor, fontSize = 25.sp)
            }
        }
    }
}
