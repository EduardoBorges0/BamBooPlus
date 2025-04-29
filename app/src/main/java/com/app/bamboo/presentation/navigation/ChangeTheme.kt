package com.app.bamboo.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.app.bamboo.R
import com.app.bamboo.presentation.viewModel.ThemeModeViewModel

@Composable
fun ChangeTheme(value: Boolean, themeModeViewModel: ThemeModeViewModel) {
    val heightSize = LocalConfiguration.current.screenHeightDp.dp
    Box(modifier = Modifier.fillMaxSize()) {
        IconButton(
            onClick = {
                if (value) {
                    themeModeViewModel.updateThemeMode(false)
                } else {
                    themeModeViewModel.updateThemeMode(true)
                }
            },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(heightSize / 20)
                .padding(top = heightSize /50)
        ) {
            AsyncImage(model = if(value) R.drawable.baseline_sunny_24 else R.drawable.baseline_dark_mode_24, contentDescription = "light")
        }
    }
}
