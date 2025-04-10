package com.app.bamboo.presentation.view.notifyView

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.bamboo.presentation.navigation.MainNavController
import com.app.bamboo.presentation.view.ui.theme.BamBooTheme
import com.app.bamboo.presentation.view.utils.notify.MainNotifyView
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
            val medicationList = notifyMedicationsViewModel.medication.observeAsState().value?.get(0)
            val time = notifyMedicationsViewModel.time.observeAsState().value
            notifyMedicationsViewModel.getMedicationById(medicationId)
            BamBooTheme {
                MainNotifyView(
                    title = medicationList?.medicationName ?: "",
                    description = medicationList?.description ?: "",
                    time = time.toString(),
                    confirmClick = {
                        notifyMedicationsViewModel.updateAccomplish(medicationId, true)
                        val intent = Intent(this, MainNavController::class.java)
                        startActivity(intent)
                        finish()
                    },
                    cancelClick = {
                        notifyMedicationsViewModel.updateAccomplish(medicationId, false)
                        val intent = Intent(this, MainNavController::class.java)
                        startActivity(intent)
                        finish()
                    }
                )
            }
        }
    }
}
