package com.app.bamboo.presentation.view.medications.nextMedication

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.bamboo.R
import com.app.bamboo.presentation.view.ui.theme.SecondaryColor
import com.app.bamboo.presentation.view.ui.theme.textColor
import com.app.bamboo.presentation.viewModel.medications.MedicationsViewModel
import kotlinx.coroutines.delay
import java.time.Duration
import java.time.LocalTime

@SuppressLint("DefaultLocale")
@Composable
fun NextMedication(
    modifier: Modifier,
    medicationsViewModel: MedicationsViewModel,
    height: Dp,
    width: Dp,
    medicationName: String,
    time: LocalTime
) {
    val currentTime = remember { mutableStateOf(LocalTime.now()) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(60_000L)
            currentTime.value = LocalTime.now()
        }
    }

    val rest = Duration.between(currentTime.value, time)
    val (hours, minutes) = medicationsViewModel.getTimeUntilNextAlarm(currentTime.value, time)

    val phrase = stringResource(R.string.left)
    val nextHour = stringResource(R.string.next_hour)
    val nextMinute = stringResource(R.string.next_minute)

    val formattedTime = if (hours == 0L) {
        String.format("$phrase %02d $nextMinute", minutes)
    } else {
        String.format("$phrase %02d $nextHour %02d $nextMinute", hours, minutes)
    }
    val nextMedicationWillBe = stringResource(R.string.next_medication_will_be)

    Box(
        modifier = modifier
            .size(width = width / 1.2f, height = height / 3)
            .clip(RoundedCornerShape(31.dp))
            .background(SecondaryColor)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = height / 14.9f)
        ) {
            Text(
                nextMedicationWillBe,
                modifier = Modifier,
                color = textColor,
                fontSize = 20.sp
            )
            Text(
                medicationName,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = textColor,
                fontSize = 20.sp
            )
        }
        Text(
            text = formattedTime,
            modifier = Modifier.align(Alignment.Center),
            color = textColor,
            fontSize = 20.sp
        )
    }
}
