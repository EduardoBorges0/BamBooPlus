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
import com.app.bamboo.presentation.view.mainScreen.insertMedications.nicheComponents.TwoOptionToggle
import com.app.bamboo.presentation.view.ui.theme.SecondaryColor
import com.app.bamboo.presentation.view.usefulCompounds.AdvancePercentage
import com.app.bamboo.presentation.view.usefulCompounds.AlertDialogComposable
import com.app.bamboo.presentation.view.usefulCompounds.BackIcon
import com.app.bamboo.presentation.view.usefulCompounds.CustomButton
import com.app.bamboo.presentation.view.usefulCompounds.CustomTextField

@Composable
fun AppointmentsOnlineOrOnsite(navController: NavController) {
    var typeOfAppointment by remember { mutableStateOf("") }
    var onlineOrOnsite by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxSize()){
        BackIcon(navController)
        AdvancePercentage(0f)
        Column(
            modifier = Modifier.align(Alignment.Center)
        ){
            CustomTextField(
                value = typeOfAppointment,
                onValueChange = { typeOfAppointment = it },
                keyboardType = KeyboardType.Text,
                isError = isError,
                label = stringResource(R.string.appointmentsType),
                modifier = Modifier.padding(horizontal = 50.dp )
            )
            TwoOptionToggle(
                value = onlineOrOnsite,
                onChange = { onlineOrOnsite = it },
                button1 = stringResource(R.string.on_site),
                button2 = stringResource(R.string.online),
                isError = isError,
                title = R.string.onlineOrOnsite,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
        CustomButton(
            onClick = {
               isError = typeOfAppointment.isEmpty() || onlineOrOnsite.isEmpty()
                if(!isError){
                    val encodedAppointment = Uri.encode(typeOfAppointment)
                    navController.navigate("hospitalAndMedicationName?typeOfAppointment=$encodedAppointment&onlineOrOnsite=$onlineOrOnsite")
                }
            },
            text = stringResource(R.string.next),
            modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth(),
            color = SecondaryColor
        )
        if(isError){
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