package com.app.bamboo.presentation.view.mainScreen.insertMedications.medicationsAndStock.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.app.bamboo.R
import com.app.bamboo.presentation.view.usefulCompounds.CustomTextField

@Composable
fun MedicationInputFields(
    modifier: Modifier,
    medicationName: String,
    description: String,
    quantityThreshold: String,
    onQuantityThreshold: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    quantity: String,
    onMedicationNameChange: (String) -> Unit,
    onQuantityChange: (String) -> Unit,
    isError: Boolean
) {
    Column(modifier = modifier) {
        CustomTextField(
            value = medicationName,
            onValueChange = onMedicationNameChange,
            modifier = Modifier.padding(horizontal = 50.dp),
            keyboardType = KeyboardType.Text,
            isError = isError,
            label = stringResource(R.string.What_medication_you_take)
        )
        CustomTextField(
            value = description,
            onValueChange = onDescriptionChange,
            modifier = Modifier.padding(top = 20.dp).padding(horizontal = 50.dp),
            keyboardType = KeyboardType.Text,
            isError = isError,
            label = stringResource(R.string.What_its_medication_make)
        )
        CustomTextField(
            value = quantity,
            onValueChange = {
                if (it.all { char -> char.isDigit() }) onQuantityChange(it)
            },
            modifier = Modifier.padding(top = 20.dp).padding(horizontal = 50.dp),
            keyboardType = KeyboardType.Number,
            isError = isError,
            label = stringResource(R.string.Do_you_quantity_in_the_stock)
        )
        CustomTextField(
            value = quantityThreshold,
            onValueChange = {
                if (it.all { char -> char.isDigit() }) onQuantityThreshold(it)
            },
            modifier = Modifier.padding(top = 20.dp).padding(horizontal = 50.dp),
            keyboardType = KeyboardType.Number,
            isError = isError,
            label = stringResource(R.string.When_must_alert_you)
        )
    }
}
