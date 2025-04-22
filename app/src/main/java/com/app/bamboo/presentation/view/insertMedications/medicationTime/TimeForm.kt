package com.app.bamboo.presentation.view.insertMedications.medicationTime

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.app.bamboo.R
import com.app.bamboo.presentation.view.usefulCompounds.CustomTextField

@Composable
fun TimeForm(
    firstTime: String,
    onFirstTimeChange: (String) -> Unit,
    hoursOrDays: String,
    onHoursOrDaysChange: (String) -> Unit,
    formattedDate: String,
    onDateClick: () -> Unit,
    modifier: Modifier,
    onFirstTimeClick: () -> Unit,
    intervalTime: String,
    onIntervalTimeChange: (String) -> Unit,
    isError: Boolean
) {
    Column(
        modifier = modifier
            .padding(horizontal = 24.dp)
            .padding(top = 115.dp)
    ) {
        CustomTextField(
            value = firstTime,
            onValueChange = onFirstTimeChange,
            modifier = Modifier.clickable { onFirstTimeClick() },
            keyboardType = KeyboardType.Text,
            isError = isError,
            isEnabledField = false,
            label = "Qual hora você começa?",
        )
        CustomTextField(
            value = formattedDate,
            onValueChange = {},
            modifier = Modifier
                .clickable { onDateClick() }
                .padding(top = 20.dp),
            keyboardType = KeyboardType.Text,
            isError = isError,
            isEnabledField = false,
            label = "Qual dia você começa?",
            trailingIcon = Icons.Filled.DateRange
        )
        HoursOrDays(hoursOrDays, onHoursOrDaysChange, isError)
        CustomTextField(
            modifier = Modifier.padding(top = 20.dp),
            value = intervalTime,
            onValueChange = onIntervalTimeChange,
            keyboardType = KeyboardType.Number,
            label = stringResource(R.string.What_interval_hour),
            isError = isError
        )
    }
}
