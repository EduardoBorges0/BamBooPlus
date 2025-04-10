package com.app.bamboo.presentation.view.utils.notify

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NotifyInfo(
    modifier: Modifier,
    medicationName: String,
    medicationTime: String,
    description: String,
) {
    Column(modifier = modifier) {
        Text(
            medicationName,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 10.dp),
            fontSize = 21.sp
        )
        Text(
            medicationTime,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 10.dp),
            fontSize = 21.sp
        )
        Text(
            description,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 10.dp),
            fontSize = 17.sp
        )
    }
}