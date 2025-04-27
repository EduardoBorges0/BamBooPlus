package com.app.bamboo.presentation.view.usefulCompounds

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.bamboo.presentation.view.ui.theme.BamBooTheme
import com.app.bamboo.presentation.view.ui.theme.Typography

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val (isDarkTheme, setIsDarkTheme) = remember { mutableStateOf(false) }
    BamBooTheme(darkTheme = isDarkTheme) {
        Column {
            Text(text = "Clique para mudar o tema")
            Button(onClick = { setIsDarkTheme(!isDarkTheme) }) {
                Text(text = if (isDarkTheme) "Tema Claro" else "Tema Escuro")
            }
        }
    }
}
