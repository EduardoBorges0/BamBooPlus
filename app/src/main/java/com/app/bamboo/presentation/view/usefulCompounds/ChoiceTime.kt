package com.app.bamboo.presentation.view.usefulCompounds

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.app.bamboo.presentation.view.ui.theme.MainColor
import com.app.bamboo.presentation.view.ui.theme.SecondaryColor
import com.app.bamboo.presentation.view.ui.theme.textColor
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChoiceTime(
    modifier: Modifier,
    onConfirm: (hour: Int, minute: Int) -> Unit,
    onDismiss: () -> Unit,
) {
    val currentTime = Calendar.getInstance()
    val locale = Locale.getDefault()

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = if(locale.language == "pt") true else false,
    )

    Column(modifier = modifier.clip(RoundedCornerShape(16.dp)).background(textColor).padding(20.dp)) {
        TimePicker(
            state = timePickerState,
            colors = TimePickerDefaults.colors(
                clockDialColor = MainColor,
                clockDialSelectedContentColor = Color.Black,
                clockDialUnselectedContentColor = textColor,
                periodSelectorSelectedContainerColor = SecondaryColor,
                periodSelectorSelectedContentColor = Color.White,
                selectorColor = textColor,
                timeSelectorSelectedContentColor = textColor,
                timeSelectorSelectedContainerColor = SecondaryColor,
                timeSelectorUnselectedContainerColor = MainColor,
                timeSelectorUnselectedContentColor = textColor
            )
        )

        Button(onClick = {
            onConfirm(timePickerState.hour, timePickerState.minute)
        }, modifier = Modifier.align(Alignment.End), colors = ButtonDefaults.buttonColors(
            containerColor = SecondaryColor
        ), shape = RoundedCornerShape(16.dp)) {
            Text("Confirm selection")
        }
    }
}
