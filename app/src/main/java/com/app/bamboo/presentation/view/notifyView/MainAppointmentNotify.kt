package com.app.bamboo.presentation.view.notifyView

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.bamboo.presentation.view.ui.theme.BamBooTheme
import com.app.bamboo.presentation.view.notifyView.notifyScreen.MainNotifyView
import com.app.bamboo.presentation.viewModel.appointment.NotifyAppointmentsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainAppointmentNotify : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val notifyAppointments: NotifyAppointmentsViewModel = hiltViewModel()
            val id = intent.getLongExtra("id", -1L)
            val appointmentsList = notifyAppointments.appointmentsList.collectAsState().value
            LaunchedEffect(id) {
                notifyAppointments.getAppointmentsById(id)
            }
            val appointment = appointmentsList.firstOrNull()
            BamBooTheme {
                if (appointment != null) {
                    MainNotifyView(
                        title = appointment.appointmentType,
                        description = "${appointment.onlineOrOnSite} / ${appointment.hospitalName}",
                        time = "${appointment.appointmentDate} | ${appointment.appointmentTime}",
                        confirmClick = {  },
                        cancelClick = {  }
                    )
                } else {
                    Text("Carregando dados da consulta...")
                }
            }
        }
    }
}
