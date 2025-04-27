package com.app.bamboo.presentation.view.mainScreen.mainMedication

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.app.bamboo.R
import com.app.bamboo.presentation.view.mainScreen.mainMedication.medicationList.components.MedicationList
import com.app.bamboo.presentation.view.mainScreen.mainMedication.withoutMedication.WithoutMedications
import com.app.bamboo.presentation.view.mainScreen.mainMedication.nextMedication.components.NextMedicationList
import com.app.bamboo.presentation.view.usefulCompounds.BackgroundMain
import com.app.bamboo.presentation.view.usefulCompounds.CustomTextField
import com.app.bamboo.presentation.view.usefulCompounds.ElevatedButtonAdd
import com.app.bamboo.presentation.viewModel.medications.MedicationsViewModel

@Composable
fun MainMedicationComposable(
    navController: NavHostController,
    medicationsViewModel: MedicationsViewModel,
    isDark: Boolean
) {
    val heightSize = LocalConfiguration.current.screenHeightDp.dp
    val widthSize = LocalConfiguration.current.screenWidthDp.dp
    val getAllmedications by medicationsViewModel.getAllMedications.collectAsState(emptyList())
    val searchQuery by medicationsViewModel.searchQuery.collectAsState()
    val searchResults by medicationsViewModel.searchResults.collectAsState()
    val nextMedication by medicationsViewModel.getNextMedication.collectAsState(emptyList())
    val isDarkTheme = isDark

    searchResults.forEach { item ->
        val medicationId = item.id
        medicationsViewModel.calculateTrueMedicationPercentage(medicationId)
    }

    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        BackgroundMain(modifier = Modifier.align(Alignment.BottomCenter))
        AsyncImage(
            model = if(isDarkTheme) R.drawable.boo else R.drawable.logo,
            contentDescription = "logo",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .height(heightSize / 5)
        )
        if (getAllmedications.isEmpty()) {
            WithoutMedications(
                heightSize, widthSize, modifier = Modifier.align(Alignment.Center),
                stringResource(R.string.Without_no_one_medicine)
            )
        } else if (searchResults.isEmpty()) {
            WithoutMedications(
                heightSize,
                widthSize,
                modifier = Modifier.align(Alignment.Center),
                stringResource(R.string.Without_no_one_medicine_in_search)
            )
        } else {
            NextMedicationList(
                modifier = Modifier.align(Alignment.Center),
                medicationsViewModel = medicationsViewModel,
                nextMedication = nextMedication,
                heightSize = heightSize,
                widthSize = widthSize
            )
            MedicationList(
                navController,
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
            trailingIcon = Icons.Filled.Search,
            label = "Buscar Medicamento"
        )
        ElevatedButtonAdd(
            modifier = Modifier.align(Alignment.BottomEnd),
            action = { navController.navigate("medicationAndStock") },
            heightSize
        )
    }
}