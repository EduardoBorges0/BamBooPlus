package com.app.bamboo.presentation.view.mainScreen.insertMedications.medicationsAndStock

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.app.bamboo.R
import com.app.bamboo.presentation.view.mainScreen.insertMedications.medicationsAndStock.components.MedicationInputFields
import com.app.bamboo.presentation.view.ui.theme.SecondaryColor
import com.app.bamboo.presentation.view.usefulCompounds.AdvancePercentage
import com.app.bamboo.presentation.view.usefulCompounds.AlertDialogComposable
import com.app.bamboo.presentation.view.usefulCompounds.BackIcon
import com.app.bamboo.presentation.view.usefulCompounds.CustomButton

@Composable
fun MedicationAndStock(navController: NavController) {
    var medicationName by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var quantityThreshold by remember { mutableStateOf("") }

    var isError by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.verticalScroll(rememberScrollState())){
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
        }
        Spacer(modifier = Modifier)

        CustomButton(
            onClick = {
                isError = medicationName.isBlank() || quantity.isBlank()
                if (!isError) {
                    val encodedMedicationName = Uri.encode(medicationName)
                    val encodedQuantity = Uri.encode(quantity)
                    val encodedDescription = Uri.encode(description)
                    navController.navigate("medicationTime?medicationName=$encodedMedicationName&quantity=$encodedQuantity&description=$encodedDescription&quantityThreshold=$quantityThreshold")
                }
            },
            text = stringResource(R.string.next),
            modifier = Modifier.fillMaxWidth(),
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
