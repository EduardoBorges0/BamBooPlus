package com.app.bamboo.presentation.view.mainScreen.medicationDetailsScreen.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.app.bamboo.R
import com.app.bamboo.presentation.view.ui.theme.MainColor
import com.app.bamboo.presentation.view.ui.theme.textColor
import com.app.bamboo.presentation.view.usefulCompounds.CustomTextField

@Composable
fun PillOrDropDetails(
    widthSize: Dp,
    heightSize: Dp,
    pillOrDrop: String,
    amountMedication: Int,
    onAmountMedicationChange: (Int) -> Unit,
    onMlChange: (Int) -> Unit
) {
    if (pillOrDrop == "Pill") {
        PlusOrMinus(
            title = stringResource(R.string.How_many_pills),
            value = amountMedication,
            heightSize = heightSize,
            widthSize = widthSize,
            onQuantityChange = onAmountMedicationChange
        )
    } else {
        CustomTextField(
            modifier = Modifier
                .padding(top = 15.dp)
                .width(widthSize / 1.5f)
                .height(heightSize / 12)
                .clip(RoundedCornerShape(25.dp)),
            textAlign = TextAlign.Center,
            value = amountMedication.toString(),
            containerColor = MainColor,
            textColor = textColor,
            labelColor = textColor,
            onValueChange = { newValue ->
                onMlChange(newValue.toIntOrNull() ?: 0)
            },
            keyboardType = KeyboardType.Number,
            isError = false,
            label = stringResource(R.string.How_many_ml)
        )

    }
}