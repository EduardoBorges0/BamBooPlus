package com.app.bamboo.presentation.view.appointments.insertAppointments

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
import com.app.bamboo.presentation.view.ui.theme.SecondaryColor
import com.app.bamboo.presentation.view.usefulCompounds.AdvancePercentage
import com.app.bamboo.presentation.view.usefulCompounds.AlertDialogComposable
import com.app.bamboo.presentation.view.usefulCompounds.BackIcon
import com.app.bamboo.presentation.view.usefulCompounds.ChoiceTime
import com.app.bamboo.presentation.view.usefulCompounds.CustomButton
import com.app.bamboo.presentation.view.usefulCompounds.CustomTextField
import com.app.bamboo.presentation.view.usefulCompounds.DatePickerModal
import com.app.bamboo.presentation.viewModel.appointment.InsertAppointmentViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@Composable
fun AppointmentTime(
    insertAppointmentViewModel: InsertAppointmentViewModel,
    navController: NavController,
    typeOfAppointment: String,
    onlineOrOnsite: String,
    doctorName: String,
    hospitalName: String
) {
    var appointmentsDate: Long by remember { mutableStateOf(0L) }
    var appointmentsTime by remember { mutableStateOf("") }

    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    var isError by remember { mutableStateOf(false) }

    val formattedDate = appointmentsDate.let {
        val locale = Locale.getDefault()
        val pattern = if (locale.language == "pt") "dd/MM/yyyy" else "MM/dd/yyyy"
        val sdf = SimpleDateFormat(pattern, locale)
        sdf.timeZone = TimeZone.getTimeZone("UTC")

        it.let { date -> sdf.format(Date(date)) }
    } ?: ""
    Box(modifier = Modifier.fillMaxSize()) {
        BackIcon(navController = navController)
        AdvancePercentage(0.75f)
        Column(modifier = Modifier.align(Alignment.Center)) {
            CustomTextField(
                value = formattedDate,
                onValueChange = {},
                modifier = Modifier
                    .clickable { showDatePicker = true }
                    .padding(top = 20.dp)
                    .padding(horizontal = 50.dp),
                keyboardType = KeyboardType.Text,
                isError = isError,
                isEnabledField = false,
                label = "Qual dia será?",
                trailingIcon = Icons.Filled.DateRange
            )
            CustomTextField(
                value = appointmentsTime,
                onValueChange = { appointmentsTime = it },
                modifier = Modifier
                    .clickable { showTimePicker = true }
                    .padding(horizontal = 50.dp)
                    .padding(top = 20.dp),
                keyboardType = KeyboardType.Text,
                isError = isError,
                isEnabledField = false,
                label = "Qual hora você começa?",
            )
        }
        CustomButton(
            onClick = {
                isError = appointmentsDate <= 0L || appointmentsTime.isEmpty()
                if(!isError){
                    insertAppointmentViewModel.insertAppointment(
                        appointmentType = typeOfAppointment,
                        onlineOrOnSite = onlineOrOnsite,
                        doctorName = doctorName,
                        hospitalName = hospitalName,
                        appointmentDate = formattedDate,
                        appointmentTime = appointmentsTime
                    )
                    navController.navigate("appointments"){
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                }
            },
            modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth(),
            text = stringResource(R.string.save),
            color = SecondaryColor
        )
        if (showDatePicker) {
            DatePickerModal(
                onDateSelected = {
                    appointmentsDate = it?.toLong() ?: 0L
                    showDatePicker = false
                },
                onDismiss = { showDatePicker = false }
            )
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
        if (showTimePicker) {
            Dialog(onDismissRequest = { showTimePicker = false }) {
                ChoiceTime(
                    modifier = Modifier.align(Alignment.Center),
                    onDismiss = { showTimePicker = false },
                    onConfirm = { hour, minute ->
                        val formatted = String.format("%02d:%02d", hour, minute)
                        appointmentsTime = formatted
                        showTimePicker = false
                    }
                )
            }
        }
    }
}