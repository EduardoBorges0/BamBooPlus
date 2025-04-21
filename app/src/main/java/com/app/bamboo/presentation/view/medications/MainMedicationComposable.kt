package com.app.bamboo.presentation.view.medications

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.app.bamboo.R
import com.app.bamboo.presentation.view.ui.theme.SecondaryColor
import com.app.bamboo.presentation.view.usefulCompounds.ElevatedButtonAdd
import com.app.bamboo.presentation.view.usefulCompounds.SearchTextField
import com.app.bamboo.presentation.viewModel.medications.MedicationsViewModel
import com.app.bamboo.utils.TimeUtils

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
    Box(modifier = Modifier.fillMaxSize()) {
        AsyncImage(
            model = R.drawable.logo,
            contentDescription = "",
            modifier = Modifier.align(Alignment.TopCenter).height(heightSize / 5)
        )
        LazyRow(modifier = Modifier.align(Alignment.Center)) {
            items(nextMedication.size) {
                NextMedication(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .padding(top = heightSize * .21f),
                    heightSize,
                    widthSize,
                    nextMedication.get(it).medicationName,
                    TimeUtils.formattedLocalDateTime(nextMedication.get(it).scheduledTime)
                )
            }
        }
        LazyRow(modifier = Modifier.align(Alignment.TopCenter)) {
            items(searchResults.size) {
                LaunchedEffect(Unit) {
                    searchResults.forEach { item ->
                        val medicationId = item.id
                        medicationsViewModel.percentMedicationsTrue(medicationId)
                    }
                }
                val percentMap by medicationsViewModel.percentMap.collectAsState()
                val times = medicationsViewModel.getMedicationsTime(it.toLong() + 1)
                    .collectAsState(emptyList())
                val id = searchResults[it].id
                val percent = percentMap[id] ?: 0f
                MedicationCards(
                    medicationName = searchResults[it].medicationName,
                    times = times.value,
                    modifier = Modifier
                        .padding(top = heightSize * .26f)
                        .padding(horizontal = 20.dp),
                    heightSize = heightSize,
                    widthSize = widthSize,
                    percent = percent
                )
            }
        }
        SearchTextField(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = heightSize * 0.09f)
                .width(widthSize / 1.15f),
            value = searchQuery,
            onValueChange = medicationsViewModel::onSearchQueryChanged,
            label = "Buscar Medicamento"
        )
        ElevatedButtonAdd(
            modifier = Modifier.align(Alignment.BottomEnd),
            action = {},
            heightSize
        )
    }
}
