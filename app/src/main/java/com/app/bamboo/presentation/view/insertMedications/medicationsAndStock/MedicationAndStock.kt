package com.app.bamboo.presentation.view.insertMedications.medicationsAndStock

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.bamboo.R
import com.app.bamboo.presentation.view.usefulCompounds.AdvancePercentage
import com.app.bamboo.presentation.view.usefulCompounds.AlertDialogComposable
import com.app.bamboo.presentation.view.usefulCompounds.BackIcon
import com.app.bamboo.presentation.view.usefulCompounds.NextButton

@Composable
fun MedicationAndStock(navController: NavController) {
    var medicationName by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var quantityThreshold by remember { mutableStateOf("") }

    var isError by remember { mutableStateOf(false) }

    val height = LocalConfiguration.current.screenHeightDp.dp
    Box(modifier = Modifier.fillMaxSize()) {
        BackIcon(
            navController
        )
        AdvancePercentage(0.00f)
        MedicationInputFields(
            quantityThreshold = quantityThreshold,
            onQuantityThreshold = { quantityThreshold = it },
            medicationName = medicationName,
            quantity = quantity,
            description = description,
            onDescriptionChange = { description = it },
            onMedicationNameChange = { medicationName = it },
            onQuantityChange = { quantity = it },
            isError = isError,
            modifier = Modifier.align(Alignment.Center)
        )
        NextButton(
            onClick = {
                isError = medicationName.isBlank() || quantity.isBlank()
                if (!isError) {
                    val encodedMedicationName = Uri.encode(medicationName)
                    val encodedQuantity = Uri.encode(quantity)
                    val encodedDescription = Uri.encode(description)
                    navController.navigate("medicationTime?medicationName=$encodedMedicationName&quantity=$encodedQuantity&description=$encodedDescription$quantityThreshold=$quantityThreshold")
                }
            },
            text = stringResource(R.string.next),
            modifier = Modifier.align(Alignment.BottomCenter)
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
