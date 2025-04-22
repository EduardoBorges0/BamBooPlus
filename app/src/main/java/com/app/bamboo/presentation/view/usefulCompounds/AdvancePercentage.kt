package com.app.bamboo.presentation.view.usefulCompounds

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.app.bamboo.R

@Composable
fun AdvancePercentage(percent: Float) {
    val height = LocalConfiguration.current.screenHeightDp.dp
    Column(
        modifier = Modifier.padding(top = height / 13)
    ) {
        AsyncImage(
            model = R.drawable.boo,
            contentDescription = "boo",
            modifier = Modifier
                .height(height / 4)
                .align(Alignment.CenterHorizontally)
        )
        LoadingProgressMedication(
            modifier = Modifier.padding(horizontal = 90.dp),
            percent = percent
        )
    }
}