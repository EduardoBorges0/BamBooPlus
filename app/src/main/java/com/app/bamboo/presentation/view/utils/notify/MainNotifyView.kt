package com.app.bamboo.presentation.view.utils.notify

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.app.bamboo.R

@Composable
fun MainNotifyView(
    title: String,
    description: String,
    time: String,
    confirmClick: () -> Unit,
    cancelClick: () -> Unit,
) {
    val heightSize = LocalConfiguration.current.screenHeightDp.dp

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Image(
            painter = painterResource(R.drawable.background),
            contentDescription = "background",
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.2f),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.boo),
                contentDescription = "boo",
                modifier = Modifier
                    .padding(top = 70.dp)
                    .size(heightSize / 3)
                    .align(Alignment.CenterHorizontally)
            )
            NotifyInfo(
                Modifier.align(Alignment.CenterHorizontally),
                medicationName = title,
                description = description,
                medicationTime = time.toString()
            )
        }
        ConfirmOrCancelButtons(
            modifier = Modifier.align(Alignment.BottomCenter),
            confirmClick = {
                confirmClick()
            },
            cancelClick = {
                cancelClick()
            })
    }
}
