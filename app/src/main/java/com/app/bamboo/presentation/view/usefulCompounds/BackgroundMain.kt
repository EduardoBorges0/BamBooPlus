package com.app.bamboo.presentation.view.usefulCompounds

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import coil.compose.AsyncImage
import com.app.bamboo.R

@Composable
fun BackgroundMain(modifier: Modifier) {
        AsyncImage(
            model = R.drawable.backgroung_main,
            contentDescription = "background_main",
            modifier = modifier.alpha(0.3f)
        )
}