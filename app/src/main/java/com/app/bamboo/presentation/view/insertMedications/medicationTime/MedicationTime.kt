package com.app.bamboo.presentation.view.insertMedications.medicationTime


import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.app.bamboo.R
import com.app.bamboo.presentation.view.usefulCompounds.AdvancePercentage
import com.app.bamboo.presentation.view.usefulCompounds.AlertDialogComposable
import com.app.bamboo.presentation.view.usefulCompounds.BackIcon
import com.app.bamboo.presentation.view.usefulCompounds.ChoiceTime
import com.app.bamboo.presentation.view.usefulCompounds.CustomTextField
import com.app.bamboo.presentation.view.usefulCompounds.DatePickerModal
import com.app.bamboo.presentation.view.usefulCompounds.NextButton
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@Composable
fun MedicationTime(navController: NavController, medicationName: String, quantity: String, description: String) {
    var selectedDate: Long? by remember { mutableStateOf(0L) }
    var firstTime: String by remember { mutableStateOf("") }
    var intervalTime: String by remember { mutableStateOf("") }
    var isCalendar: Boolean by remember { mutableStateOf(false) }
    var isChoiceTime: Boolean by remember { mutableStateOf(false) }
    var isError: Boolean by remember { mutableStateOf(false) }
    var hourOrDays: String by remember { mutableStateOf("") }

    val formattedDate = selectedDate?.let {
        val locale = Locale.getDefault()
        val pattern = if (locale.language == "pt") "dd/MM/yyyy" else "MM/dd/yyyy"
        val sdf = SimpleDateFormat(pattern, locale)
        sdf.timeZone = TimeZone.getTimeZone("UTC")

        sdf.format(Date(it))
    } ?: ""


    Box(modifier = Modifier.fillMaxSize()) {
        BackIcon(navController)
        AdvancePercentage(0.5f)
        TimeForm(
            firstTime = firstTime,
            onFirstTimeChange = { firstTime = it },
            hoursOrDays = hourOrDays,
            onHoursOrDaysChange = { hourOrDays = it },
            formattedDate = formattedDate,
            onDateClick = { isCalendar = true },
            onFirstTimeClick = { isChoiceTime = true },
            intervalTime = intervalTime,
            onIntervalTimeChange = { intervalTime = it },
            isError = isError,
            modifier = Modifier.align(Alignment.Center)
        )
        NextButton(
            onClick = {
                isError = firstTime.isBlank() || hourOrDays.isEmpty() || intervalTime.isBlank()
                if(!isError){
                    navController.navigate(
                        "pillOrDrop?medicationName=$medicationName&quantity=$quantity&firstTime=$firstTime&selectedDate=$formattedDate&hoursOrDays=$hourOrDays&intervalTime=$intervalTime&description=$description"
                    )
                }
            },
            text = stringResource(R.string.next),
            modifier = Modifier.align(Alignment.BottomCenter)
        )

        if (isCalendar) {
            DatePickerModal(
                { selectedDate = it },
                { isCalendar = false }
            )
        }
        if (isChoiceTime) {
            Dialog(onDismissRequest = { isChoiceTime = false }) {
                ChoiceTime(
                    modifier = Modifier.align(Alignment.Center),
                    onDismiss = { isChoiceTime = false },
                    onConfirm = { hour, minute ->
                        val formatted = String.format("%02d:%02d", hour, minute)
                        firstTime = formatted
                        isChoiceTime = false
                    }
                )
            }
        }
        if (isError) {
            AlertDialogComposable(
                onConfirm = { isError = false },
                onDismiss = { isError = false },
                title = stringResource(R.string.fill_everything),
                text = stringResource(R.string.fill_every_fields),
                confirmText = stringResource(R.string.try_again),
                dismissText = "",
                needDismiss = false
            )
        }
    }
}