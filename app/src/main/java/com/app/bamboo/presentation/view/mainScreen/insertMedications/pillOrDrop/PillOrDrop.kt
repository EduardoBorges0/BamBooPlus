package com.app.bamboo.presentation.view.mainScreen.insertMedications.pillOrDrop

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
import com.app.bamboo.presentation.view.mainScreen.insertMedications.nicheComponents.TwoOptionToggle
import com.app.bamboo.presentation.viewModel.medications.InsertMedicationsViewModel

@Composable
fun PillOrDrop(
    navController: NavController,
    description: String,
    insertMedicationsViewModel: InsertMedicationsViewModel,
    medicationName: String,
    quantity: String,
    quantityThreshold: String,
    firstTime: String,
    selectedDate: String,
    hoursOrDay: String,
    intervalTime: String
) {
    var pillOrDrop by remember { mutableStateOf("") }
    var howMany: String by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        BackIcon(navController)
        AdvancePercentage(0.75f)
        Column(
            modifier = Modifier.align(Alignment.Center)
        ) {
            TwoOptionToggle(
                value = pillOrDrop,
                onChange = { pillOrDrop = it },
                button1 = stringResource(R.string.Pills),
                button2 = stringResource(R.string.Drop),
                isError = isError,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                title = R.string.pillsOrDrop
            )
            CustomTextField(
                modifier = Modifier.padding(top = 20.dp).padding(horizontal = 50.dp),
                value = howMany,
                onValueChange = { howMany = it },
                keyboardType = KeyboardType.Number,
                isError = isError,
                label = if (pillOrDrop == "Pill") stringResource(R.string.How_many_pills) else stringResource(
                    R.string.How_many_ml
                )
            )
        }
        CustomButton(
            onClick = {
                isError = howMany.isBlank()
                if(!isError){
                    insertMedicationsViewModel.insertMedication(
                        medicationName = medicationName,
                        description = description,
                        pillOrDrop = pillOrDrop,
                        daysOrHour = hoursOrDay,
                        medicationTime = firstTime,
                        date = selectedDate,
                        quantity = quantity.toInt(),
                        time = intervalTime.toLong(),
                        quantityThreshold = quantityThreshold.toInt(),
                        amountMedication = howMany.toInt()
                    )
                    navController.navigate("main"){
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                    }
                }
            },
            text = stringResource(R.string.confirm),
            modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter),
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