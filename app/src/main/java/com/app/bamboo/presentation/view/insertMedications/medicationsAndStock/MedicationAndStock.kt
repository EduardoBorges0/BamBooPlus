package com.app.bamboo.presentation.view.insertMedications.medicationsAndStock

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import coil.compose.AsyncImage
import com.app.bamboo.R
import com.app.bamboo.presentation.view.usefulCompounds.AlertDialogComposable
import com.app.bamboo.presentation.view.usefulCompounds.BackIcon
import com.app.bamboo.presentation.view.usefulCompounds.LoadingProgressMedication

@Composable
fun MedicationAndStock(navController: NavController) {
    var medicationName by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    val height = LocalConfiguration.current.screenHeightDp.dp
    Box(modifier = Modifier.fillMaxSize()) {
        BackIcon(
            modifier = Modifier
                .padding(39.dp)
                .padding(top = 25.dp)
                .clickable {
                    navController.popBackStack()
                }
        )
        Column(
            modifier = Modifier.padding(top = 90.dp)
        ) {
            AsyncImage(
                model = R.drawable.boo,
                contentDescription = "boo",
                modifier = Modifier
                    .height(height / 4)
                    .align(Alignment.CenterHorizontally)
            )
            LoadingProgressMedication(
                modifier = Modifier.padding(horizontal = 90.dp),
                percent = 0.0f
            )
        }
        MedicationInputFields(
            medicationName = medicationName,
            quantity = quantity,
            onMedicationNameChange = { medicationName = it },
            onQuantityChange = { quantity = it },
            isError = isError,
            modifier = Modifier.align(Alignment.Center)
        )
        NextButton(
            onClick = {
                isError = medicationName.isBlank() || quantity.isBlank()
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
