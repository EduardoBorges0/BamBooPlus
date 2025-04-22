package com.app.bamboo.presentation.view.medications

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.app.bamboo.R
import com.app.bamboo.presentation.view.medications.medicationList.MedicationList
import com.app.bamboo.presentation.view.medications.medicationList.WithoutMedications
import com.app.bamboo.presentation.view.medications.nextMedication.NextMedicationList
import com.app.bamboo.presentation.view.usefulCompounds.CustomTextField
import com.app.bamboo.presentation.view.usefulCompounds.ElevatedButtonAdd
import com.app.bamboo.presentation.viewModel.medications.MedicationsViewModel

@Composable
fun MainMedicationComposable(
    navController: NavHostController,
    medicationsViewModel: MedicationsViewModel
) {
    val heightSize = LocalConfiguration.current.screenHeightDp.dp
    val widthSize = LocalConfiguration.current.screenWidthDp.dp
    val searchQuery by medicationsViewModel.searchQuery.collectAsState()
    val searchResults by medicationsViewModel.searchResults.collectAsState()
    val nextMedication by medicationsViewModel.getNextMedication.collectAsState(emptyList())
    LaunchedEffect(Unit) {
        searchResults.forEach { item ->
            val medicationId = item.id
            medicationsViewModel.percentMedicationsTrue(medicationId)
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        AsyncImage(
            model = R.drawable.logo,
            contentDescription = "logo",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .height(heightSize / 5)
        )
        NextMedicationList(
            modifier = Modifier.align(Alignment.Center),
            nextMedication,
            heightSize,
            widthSize
        )
        if (searchResults.isEmpty()) {
            WithoutMedications()
        } else {
            MedicationList(
                modifier = Modifier.align(Alignment.TopCenter),
                searchResults,
                medicationsViewModel,
                heightSize,
                widthSize
            )
        }

        CustomTextField(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 60.dp)
                .padding(top = heightSize * 0.09f)
                .width(widthSize / 1.15f),
            value = searchQuery,
            onValueChange = medicationsViewModel::onSearchQueryChanged,
            keyboardType = KeyboardType.Text,
            isError = false,
            label = "Buscar Medicamento"
        )
        ElevatedButtonAdd(
            modifier = Modifier.align(Alignment.BottomEnd),
            action = { navController.navigate("medicationAndStock") },
            heightSize
        )
    }
}