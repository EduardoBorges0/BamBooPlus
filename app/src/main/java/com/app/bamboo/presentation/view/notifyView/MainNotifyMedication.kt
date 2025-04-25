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
import com.app.bamboo.presentation.view.notifyView.notifyScreen.MainNotifyView
import com.app.bamboo.presentation.viewModel.medications.NotifyMedicationsViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate

@AndroidEntryPoint
class MainNotifyMedication : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val medicationId = intent.getLongExtra("id", -1L)
        setContent {
            val notifyMedicationsViewModel: NotifyMedicationsViewModel = hiltViewModel()
            notifyMedicationsViewModel.getMedicationById(medicationId)
            val medicationList = notifyMedicationsViewModel.medication.observeAsState().value?.get(0)
            val time = notifyMedicationsViewModel.time.observeAsState().value
            BamBooTheme {
                notifyMedicationsViewModel.insertMedicationHistory(
                    medicationId = medicationId,
                    medicationName = medicationList?.medicationName.toString(),
                    medicationTime = time.toString(),
                    dayOfWeek = LocalDate.now().dayOfWeek.toString()
                )
                MainNotifyView(
                    title = medicationList?.medicationName ?: "",
                    description = medicationList?.description ?: "",
                    time = time.toString(),
                    confirmClick = {
                        notifyMedicationsViewModel.updateAccomplishSchedule(medicationId, true)
                        notifyMedicationsViewModel.updateQuantity(medicationList?.medicationId ?: 0)
                        notifyMedicationsViewModel.insertMedicationHistory(medicationId,
                            medicationList?.medicationName ?: "",
                            time.toString(),
                            LocalDate.now().dayOfWeek.value.toString())
                        val intent = Intent(this, MainNavController::class.java)
                        startActivity(intent)
                        finish()
                    },
                    cancelClick = {
                        notifyMedicationsViewModel.updateAccomplishSchedule(medicationId, false)
                        val intent = Intent(this, MainNavController::class.java)
                        startActivity(intent)
                        finish()
                    }
                )
            }
        }
    }
}
