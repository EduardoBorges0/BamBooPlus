package com.app.bamboo.presentation.view.appointments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AppointmentsCard(
    heightSize: Dp,
    widthSize: Dp,
    typeOfAppointments: String,
    time: String,
    date: String,
    hospitalName: String
) {
    Box(
        modifier = Modifier
            .size(height = heightSize / 5, width = widthSize / 1.5f)
            .clip(RoundedCornerShape(31.dp))
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Text(
            typeOfAppointments,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 40.dp),
            fontSize = 20.sp
        )
        Row(modifier = Modifier.align(Alignment.Center)) {
            Text(
                date,
                fontSize = 16.sp
            )
            Text("|", modifier = Modifier.padding(horizontal = 12.dp), fontSize = 20.sp)
            Text(
                time,
                fontSize = 16.sp
            )
        }
        Text(
            hospitalName,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(top = 60.dp),
            fontSize = 18.sp
        )

    }
}