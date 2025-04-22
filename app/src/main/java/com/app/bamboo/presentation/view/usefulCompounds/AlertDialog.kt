package com.app.bamboo.presentation.view.usefulCompounds

import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.ui.graphics.Color


@Composable
fun AlertDialogComposable(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    title: String,
    confirmText: String,
    dismissText: String,
    needDismiss: Boolean,
    text: String
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(confirmText, color = Color.Black)
            }
        },
        dismissButton = if (needDismiss) {
            {
                TextButton(onClick = onDismiss) {
                    Text(dismissText)
                }
            }
        } else null,
        title = {
            Text(title)
        },
        text = {
            Text(text)
        },
        modifier = Modifier,

    )
}
