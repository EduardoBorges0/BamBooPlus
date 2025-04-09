package com.app.bamboo.domain.notifications

import android.content.Context
import com.app.bamboo.presentation.view.notifyMedicationView.MainNotifyMedication
import com.app.bamboo.utils.NotificationUtils

object ShowNotification {
    fun showMedicationNotification(context: Context, id: Long, medicationName: String) {
       NotificationUtils.showNotification(
           context = context,
           channelId = "medication_notification",
           channelName = "Medication Channel",
           descriptionChannel = "Alarm Medication",
           activity = MainNotifyMedication::class.java,
           notificationTitle = "Está na hora $medicationName",
           notificationDescription = "Clique aqui para confirmar",
           id = id
       )
    }
    fun showAppointmentNotification(context: Context) {
        NotificationUtils.showNotification(
            context = context,
            channelId = "appointment_notification",
            channelName = "Appointment Channel",
            descriptionChannel = "Alarm Appointment",
            activity = MainNotifyMedication::class.java,
            notificationTitle = "Está na hora da sua consulta",
            notificationDescription = "Clique aqui para confirmar",
            id = 1
        )
    }
}