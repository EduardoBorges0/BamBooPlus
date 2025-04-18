package com.app.bamboo.presentation.view.notifyView.notifyScreen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.app.bamboo.R
import com.app.bamboo.presentation.view.ui.theme.NotifyCancelButton
import com.app.bamboo.presentation.view.ui.theme.NotifyConfirmButton

@Composable
fun ConfirmOrCancelButtons(confirmClick: () -> Unit, cancelClick: () -> Unit, modifier: Modifier) {
    val heigthSize = LocalConfiguration.current.screenHeightDp.dp
    Row(
        modifier = modifier.padding(bottom = 230.dp)
    ) {
        Button(
            onClick = confirmClick,
            modifier = Modifier
                .padding(horizontal = 30.dp)
                .size(heigthSize / 10),
            colors = ButtonDefaults.buttonColors(
                containerColor = NotifyConfirmButton, contentColor = Color.White
            )
        ) {
            Icon(
                painter = painterResource(R.drawable.check),
                contentDescription = "check",
                modifier = Modifier.fillMaxSize(),
                tint = Color.White
            )
        }
        Button(
            onClick = cancelClick,
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .size(heigthSize / 10),
            colors = ButtonDefaults.buttonColors(
                containerColor = NotifyCancelButton
            )
        ) {
            Icon(
                painter = painterResource(R.drawable.cancel),
                contentDescription = "check",
                modifier = Modifier.fillMaxSize(),
                tint = Color.White
            )
        }
    }
}