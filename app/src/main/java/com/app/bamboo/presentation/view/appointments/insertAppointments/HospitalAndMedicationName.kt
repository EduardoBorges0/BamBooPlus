package com.app.bamboo.presentation.view.appointments.insertAppointments

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.navigation.NavController
import com.app.bamboo.R
import com.app.bamboo.presentation.view.ui.theme.SecondaryColor
import com.app.bamboo.presentation.view.usefulCompounds.AdvancePercentage
import com.app.bamboo.presentation.view.usefulCompounds.AlertDialogComposable
import com.app.bamboo.presentation.view.usefulCompounds.BackIcon
import com.app.bamboo.presentation.view.usefulCompounds.CustomButton
import com.app.bamboo.presentation.view.usefulCompounds.CustomTextField

@Composable
fun HospitalAndMedicationName(
    navController: NavController,
    typeOfAppointment: String,
    onlineOrOnsite: String
) {
    var doctorName by remember { mutableStateOf("") }
    var hospitalName by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxSize()) {
        BackIcon(navController = navController)
        AdvancePercentage(0.5f)
        Column(
            modifier = Modifier.align(Alignment.Center)
        ) {
            CustomTextField(
                value = doctorName,
                onValueChange = { doctorName = it },
                keyboardType = KeyboardType.Text,
                isError = isError,
                modifier = Modifier.padding(horizontal = 50.dp),
                label = stringResource(R.string.which_name_of_doctor)
            )
            CustomTextField(
                value = hospitalName,
                onValueChange = { hospitalName = it },
                keyboardType = KeyboardType.Text,
                isError = isError,
                modifier = Modifier
                    .padding(horizontal = 50.dp)
                    .padding(top = 20.dp),
                label = stringResource(R.string.which_name_of_hospital)
            )
        }
        CustomButton(
            onClick = {
                isError = doctorName.isEmpty() || hospitalName.isEmpty()
                if (!isError) {
                    val encodedDoctorName = Uri.encode(doctorName)
                    val encodedHospitalName = Uri.encode(hospitalName)
                    navController.navigate("hospitalAndMedicationName?typeOfAppointment=$typeOfAppointment&onlineOrOnsite=$onlineOrOnsite&doctorName=${encodedDoctorName}&hospitalName=${encodedHospitalName}")
                }
            },
            text = stringResource(R.string.next),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            color = SecondaryColor
        )
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