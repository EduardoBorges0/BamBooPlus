package com.app.bamboo.presentation.view.insertMedications

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.app.bamboo.R
import com.app.bamboo.presentation.view.ui.theme.SecondaryColor
import com.app.bamboo.presentation.view.usefulCompounds.AlertDialogComposable
import com.app.bamboo.presentation.view.usefulCompounds.BackIcon
import com.app.bamboo.presentation.view.usefulCompounds.LoadingProgressMedication
import com.app.bamboo.presentation.view.usefulCompounds.SearchTextField

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
                .clickable{
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
        Column(modifier = Modifier.align(Alignment.Center)) {
            SearchTextField(
                value = medicationName,
                onValueChange = { medicationName = it },
                modifier = Modifier,
                keyboardType = KeyboardType.Text,
                isError = isError,
                label = stringResource(R.string.What_medication_you_take)
            )
            SearchTextField(
                value = quantity,
                onValueChange = {
                    if (it.all { char -> char.isDigit() }) {
                        quantity = it
                    }
                },
                modifier = Modifier.padding(top = 20.dp),
                keyboardType = KeyboardType.Number,
                isError = isError,
                label = stringResource(R.string.Do_you_quantity_in_the_stock)
            )
        }
        Button(
            onClick = {
              if(medicationName == "" || quantity == ""){
                  isError = true
              }else{
                  isError = false
              }
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(80.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = SecondaryColor
            ),
            shape = RoundedCornerShape(23.dp)
        ) {
            Text(stringResource(R.string.next))
        }
        if(isError){
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