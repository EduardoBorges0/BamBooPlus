package com.app.bamboo.presentation.view.usefulCompounds

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.app.bamboo.presentation.view.ui.theme.textColor

@Composable
fun CustomTextField(
    modifier: Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType,
    trailingIcon: ImageVector? = null,
    isEnabledField: Boolean = true,
    containerColor: Color = Color.Unspecified,
    textColor: Color = Color.Black,
    labelColor: Color = Color.Black,
    isError: Boolean,
    label: String = "Buscar"
) {
    val widthSize = LocalConfiguration.current.screenWidthDp.dp
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = if(isError) Color.Red else Color.Black) },
        modifier = modifier.then(
            if (isError) {
                Modifier.border(2.dp, Color.Red, RoundedCornerShape(10.dp))
            } else {
                Modifier.border(0.dp, Color.Transparent, RoundedCornerShape(10.dp))
            }
        ).width(widthSize),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType
        ),
        enabled = isEnabledField,
        trailingIcon = trailingIcon?.let {
            { Icon(it, contentDescription = null) }
        },
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.colors(
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            disabledTextColor = textColor,
            focusedIndicatorColor = Color.Transparent,
            focusedTextColor = textColor,
            focusedLabelColor = labelColor,
            unfocusedContainerColor = containerColor,
            focusedContainerColor = containerColor
        )
    )
}
