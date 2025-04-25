package com.app.bamboo.presentation.view.medications.medicationDetailsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.app.bamboo.presentation.view.ui.theme.MainColor
import com.app.bamboo.presentation.view.ui.theme.NotifyCancelButton
import com.app.bamboo.presentation.view.ui.theme.SecondaryColor
import com.app.bamboo.presentation.view.ui.theme.textColor
import com.app.bamboo.presentation.view.usefulCompounds.BackIcon
import com.app.bamboo.presentation.view.usefulCompounds.BackgroundMain
import com.app.bamboo.presentation.view.usefulCompounds.CustomTextField
import com.app.bamboo.presentation.view.usefulCompounds.NextButton
import com.app.bamboo.presentation.viewModel.medications.MedicationDetailsViewModel
import com.app.bamboo.presentation.viewModel.medications.MedicationsViewModel

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
        Box(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
            BackgroundMain(modifier = Modifier.align(Alignment.BottomCenter))
            BackIcon(navController)
            Column(modifier = Modifier.align(Alignment.Center).padding(top = heightSize / 6)) {
                HowManyMedicine(modifier = Modifier, getMedicationsById, times)
                if (getMedicationsById.pillOrDrop == "Pills") {
                    PlusOrMinus("Quantas Pilula:", getMedicationsById.amountMedication)
                }else{
                    CustomTextField(
                        modifier = Modifier
                            .padding(top = 15.dp)
                            .width(widthSize / 1.5f)
                            .height(heightSize / 12)
                            .clip(RoundedCornerShape(16.dp)),
                        value = "ss",
                        containerColor = MainColor,
                        textColor = textColor,
                        labelColor = textColor,
                        onValueChange = {},
                        keyboardType = KeyboardType.Number,
                        isError = false,
                        label = "Quantas mg?"
                    )
                }
                PlusOrMinus("Estoque:", getMedicationsById.quantity)
                PlusOrMinus(
                    "Alerta quando tiver isso no estoque:",
                    getMedicationsById.quantityThreshold
                )
                NextButton(
                    onClick = { },
                    text = "Salvar",
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .width(widthSize / 1.5f)
                        .height(heightSize / 12)
                        .clip(RoundedCornerShape(16.dp)),
                    color = SecondaryColor
                )
                NextButton(
                    onClick = {},
                    text = "Deletar",
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .width(widthSize / 1.5f)
                        .height(heightSize / 12)
                        .clip(RoundedCornerShape(16.dp)),
                    color = NotifyCancelButton
                )
            }
        }
    } else {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                "Carregando...",
                modifier = Modifier.align(Alignment.Center),
                fontSize = 18.sp,
                color = Color.Gray
            )
        }
    }
}
