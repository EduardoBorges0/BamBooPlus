package com.app.bamboo.presentation.view.medications.medicationDetailsScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.bamboo.R
import com.app.bamboo.presentation.view.medications.medicationDetailsScreen.components.HowManyMedicine
import com.app.bamboo.presentation.view.medications.medicationDetailsScreen.components.MedicationDetailsButtons
import com.app.bamboo.presentation.view.medications.medicationDetailsScreen.components.PillOrDropDetails
import com.app.bamboo.presentation.view.medications.medicationDetailsScreen.components.PlusOrMinus
import com.app.bamboo.presentation.view.ui.theme.NotifyCancelButton
import com.app.bamboo.presentation.view.ui.theme.SecondaryColor
import com.app.bamboo.presentation.view.usefulCompounds.AlertDialogComposable
import com.app.bamboo.presentation.view.usefulCompounds.BackIcon
import com.app.bamboo.presentation.view.usefulCompounds.BackgroundMain
import com.app.bamboo.presentation.viewModel.medications.MedicationDetailsViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MainMedicationDetailsScreen(
    navController: NavController,
    medicationDetailsViewModel: MedicationDetailsViewModel
) {
    val medications = medicationDetailsViewModel.everyMedications.collectAsState().value
    val times = medicationDetailsViewModel.everyMedicationsTime.collectAsState().value
    val widthSize = LocalConfiguration.current.screenWidthDp.dp
    val heightSize = LocalConfiguration.current.screenHeightDp.dp

    if (medications.isNotEmpty()) {
        val getMedicationsById = medications[0]
        var quantity by remember { mutableIntStateOf(getMedicationsById.quantity) }
        var quantityThreshold by remember { mutableIntStateOf(getMedicationsById.quantityThreshold) }
        var amountMedication by remember { mutableIntStateOf(getMedicationsById.amountMedication) }
        var isChange by remember { mutableStateOf(false) }
        var isDeleted by remember { mutableStateOf(false) }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            BackgroundMain(modifier = Modifier.align(Alignment.BottomCenter))
            BackIcon(navController, onClick = {
                if (getMedicationsById.quantity != quantity
                    || getMedicationsById.amountMedication != amountMedication
                    || getMedicationsById.quantityThreshold != quantityThreshold
                ) {
                    isChange = true
                } else {
                    navController.popBackStack()
                }
            })
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = heightSize / 6)
            ) {
                HowManyMedicine(
                    getMedicationsById.medicationName,
                    getMedicationsById.description,
                    times,
                    widthSize = widthSize
                )
                PillOrDropDetails(
                    widthSize = widthSize,
                    heightSize = heightSize,
                    pillOrDrop = getMedicationsById.pillOrDrop,
                    amountMedication = amountMedication,
                    onAmountMedicationChange = { amountMedication = it },
                    onMlChange = {
                        amountMedication = it.toInt()
                    }
                )
                PlusOrMinus(
                    title = stringResource(R.string.stock),
                    value = quantity,
                    heightSize = heightSize,
                    widthSize = widthSize,
                    onQuantityChange = { quantity = it }
                )
                PlusOrMinus(
                    title = stringResource(R.string.stock_alert),
                    value = quantityThreshold,
                    heightSize = heightSize,
                    widthSize = widthSize,
                    onQuantityChange = { quantityThreshold = it }
                )
                MedicationDetailsButtons(
                    widthSize,
                    heightSize,
                    stringResource(R.string.save),
                    SecondaryColor,
                    onClick = {
                        medicationDetailsViewModel.updateMedications(
                            id = getMedicationsById.id,
                            medicationName = getMedicationsById.medicationName,
                            description = getMedicationsById.description,
                            pillOrDrop = getMedicationsById.pillOrDrop,
                            daysOrHour = getMedicationsById.daysOrHours,
                            medicationTime = getMedicationsById.medicationTime,
                            date = getMedicationsById.date,
                            quantity = quantity,
                            time = getMedicationsById.time,
                            quantityThreshold = quantityThreshold,
                            amountMedication = amountMedication
                        )
                        navController.popBackStack()
                    })
                MedicationDetailsButtons(
                    widthSize,
                    heightSize,
                    stringResource(R.string.deletar),
                    NotifyCancelButton,
                    onClick = {
                        isDeleted = true
                    })
            }
        }
        if (isChange) {
            AlertDialogComposable(
                onConfirm = {
                    medicationDetailsViewModel.updateMedications(
                        id = getMedicationsById.id,
                        medicationName = getMedicationsById.medicationName,
                        description = getMedicationsById.description,
                        pillOrDrop = getMedicationsById.pillOrDrop,
                        daysOrHour = getMedicationsById.daysOrHours,
                        medicationTime = getMedicationsById.medicationTime,
                        date = getMedicationsById.date,
                        quantity = quantity,
                        time = getMedicationsById.time,
                        quantityThreshold = quantityThreshold,
                        amountMedication = amountMedication
                    )
                    navController.popBackStack()
                },
                onDismiss = {
                    navController.popBackStack()
                },
                needDismiss = true,
                title = "Você quer salva suas mudanças?",
                text = "Você fez alterações e não salvou!!",
                confirmText = stringResource(R.string.save),
                dismissText = "Não quero salvar"
            )
        }
        if (isDeleted) {
            AlertDialogComposable(
                onConfirm = {
                    medicationDetailsViewModel.deleteMedications(getMedicationsById.id)
                    navController.popBackStack()
                },
                onDismiss = {
                    isDeleted = false
                },
                needDismiss = true,
                title = "Você quer deletar este remédio?",
                text = "",
                confirmText = stringResource(R.string.deletar),
                dismissText = "Cancelar"
            )
        }
    } else {

    }
}
