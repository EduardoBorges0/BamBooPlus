package com.app.bamboo.presentation.view.notifyMedicationView

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.app.bamboo.R
import com.app.bamboo.presentation.view.ui.theme.BamBooTheme
import com.app.bamboo.presentation.view.ui.theme.NotifyCancelButton
import com.app.bamboo.presentation.view.ui.theme.NotifyConfirmButton
import com.app.bamboo.presentation.viewModel.medications.NotifyMedicationsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainNotifyMedication : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val medicationId = intent.getLongExtra("medication_id", -1L)
        setContent {
            val notifyMedicationsViewModel: NotifyMedicationsViewModel = hiltViewModel()
            notifyMedicationsViewModel.getMedicationById(medicationId)
            val navHostController = rememberNavController()
            BamBooTheme {
                MainNotifyMedicationComposable(
                    notifyMedicationsViewModel,
                    medicationId,
                    navHostController
                    )
            }
        }
    }
}

@Composable
fun MainNotifyMedicationComposable(
    notifyMedicationsViewModel: NotifyMedicationsViewModel,
    id: Long,
    navHostController: NavHostController,
) {
    val heightSize = LocalConfiguration.current.screenHeightDp.dp
    val medicationList = notifyMedicationsViewModel.medication.observeAsState().value?.get(0)
    val time = notifyMedicationsViewModel.time.observeAsState().value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Image(
            painter = painterResource(R.drawable.background),
            contentDescription = "background",
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.2f),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.boo),
                contentDescription = "boo",
                modifier = Modifier
                    .padding(top = 70.dp)
                    .size(heightSize / 3)
                    .align(Alignment.CenterHorizontally)
            )
            NotifyInfo(
                Modifier.align(Alignment.CenterHorizontally),
                medicationName = medicationList?.medicationName ?: "Sem nome",
                description = medicationList?.description ?: "Sem descrição",
                medicationTime = time.toString()
            )
        }
        ConfirmOrCancelButtons(
            modifier = Modifier.align(Alignment.BottomCenter),
            confirmClick = {
                notifyMedicationsViewModel.updateAccomplish(id, true)
                navHostController.navigate("mainMedication")
            },
            cancelClick = {
                notifyMedicationsViewModel.updateAccomplish(id, false)
                navHostController.navigate("mainMedication")
            })
    }
}
