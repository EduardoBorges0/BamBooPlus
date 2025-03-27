package com.app.bamboo.presentation.notifications

import android.content.Context
import com.app.bamboo.presentation.view.SuaActivity
import com.app.bamboo.utils.NotificationUtils

object ShowNotification {
    fun showMedicationNotification(context: Context) {
       NotificationUtils.showNotification(
           context = context,
           channelId = "medication_notification",
           notificationId = 1,
           channelName = "Medication Channel",
           descriptionChannel = "Alarm Medication",
           activity = SuaActivity::class.java,
           notificationtitle = "Está na hora da sua medicação",
           notificationDescription = "Clique aqui para confirmar"
       )
    }
}