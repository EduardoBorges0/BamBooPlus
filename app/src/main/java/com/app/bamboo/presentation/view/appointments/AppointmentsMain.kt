package com.app.bamboo.presentation.view.appointments

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.bamboo.presentation.navigation.MainNavController
import com.app.bamboo.presentation.view.usefulCompounds.CustomTextField
import com.app.bamboo.presentation.view.usefulCompounds.ElevatedButtonAdd
import com.app.bamboo.presentation.viewModel.appointment.AppointmentsViewModel

@Composable
fun AppointmentsMain(appointmentsViewModel: AppointmentsViewModel, navController: NavController) {
    val searchQuery = appointmentsViewModel.searchQuery.collectAsState().value
    val heightSize = LocalConfiguration.current.screenHeightDp.dp
    val widthSize = LocalConfiguration.current.screenWidthDp.dp

    Box(modifier = Modifier.fillMaxSize()) {
        CustomTextField(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 60.dp)
                .padding(top = heightSize * 0.09f)
                .width(widthSize / 1.15f),
            value = searchQuery,
            onValueChange = appointmentsViewModel::onSearchQueryChanged,
            keyboardType = KeyboardType.Text,
            isError = false,
            trailingIcon = Icons.Filled.Search,
            label = "Buscar Consulta"
        )
        ElevatedButtonAdd(
            modifier = Modifier.align(Alignment.BottomEnd),
            action = {
               navController.navigate("appointmentsOnlineOrSite")
            },
            heightSize = heightSize
        )
    }
}