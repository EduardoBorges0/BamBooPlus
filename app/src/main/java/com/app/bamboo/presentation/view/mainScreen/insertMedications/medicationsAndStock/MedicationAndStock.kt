package com.app.bamboo.presentation.view.mainScreen.insertMedications.medicationsAndStock

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.bamboo.R
import com.app.bamboo.presentation.view.mainScreen.insertMedications.medicationsAndStock.components.MedicationInputFields
import com.app.bamboo.presentation.view.ui.theme.SecondaryColor
import com.app.bamboo.presentation.view.usefulCompounds.AdvancePercentage
import com.app.bamboo.presentation.view.usefulCompounds.AlertDialogComposable
import com.app.bamboo.presentation.view.usefulCompounds.BackIcon
import com.app.bamboo.presentation.view.usefulCompounds.CustomButton
import com.app.bamboo.presentation.viewModel.medications.InsertMedicationsViewModel

@Composable
fun MedicationAndStock(navController: NavController, insertMedicationsViewModel: InsertMedicationsViewModel) {
    var medicationName by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var quantityThreshold by remember { mutableStateOf("") }

    var isError by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        Box(modifier = Modifier){


        }
        Column(modifier = Modifier.wrapContentHeight()) {
            Spacer(modifier = Modifier.height(40.dp))
            BackIcon(
                navController,
                modifier = Modifier.align(Alignment.Start).padding(26.dp).padding(start = 20.dp)
            )
            AdvancePercentage(0.00f)

            Spacer(modifier = Modifier.height(50.dp))

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
                modifier = Modifier
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        CustomButton(
            onClick = {
                isError = medicationName.isBlank() || quantity.isBlank()
                if (!isError) {
                    val encodedMedicationName = Uri.encode(medicationName)
                    val encodedQuantity = Uri.encode(quantity)
                    val encodedDescription = Uri.encode(description)
                    insertMedicationsViewModel.updateMedicationsBasic(
                        name = medicationName,
                        function = description,
                        stock = quantity.toLongOrNull() ?: 0L,
                        warningStock = quantityThreshold.toIntOrNull() ?: 0
                    )
                    navController.navigate("medicationTime")
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
