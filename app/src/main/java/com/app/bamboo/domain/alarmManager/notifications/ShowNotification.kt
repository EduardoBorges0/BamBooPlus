package com.app.bamboo.domain.alarmManager.notifications

import android.content.Context
import com.app.bamboo.presentation.view.notifyView.MainAppointmentNotify
import com.app.bamboo.presentation.view.notifyView.MainNotifyMedication
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
    fun showAppointmentNotification(context: Context, id: Long?, appointmentType: String, time: String) {
        NotificationUtils.showNotification(
            context = context,
            channelId = "appointment_notification",
            channelName = "Appointment Channel",
            descriptionChannel = "Alarm Appointment",
            activity = MainAppointmentNotify::class.java,
            notificationTitle = "$appointmentType às $time",
            notificationDescription = "Clique aqui para confirmar",
            id = id
        )
    }
}