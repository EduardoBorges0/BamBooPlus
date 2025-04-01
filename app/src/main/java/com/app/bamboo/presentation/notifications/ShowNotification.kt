package com.app.bamboo.presentation.notifications

import android.content.Context
import android.util.Log
import com.app.bamboo.presentation.view.SuaActivity
import com.app.bamboo.utils.NotificationUtils
import dagger.hilt.android.EntryPointAccessors
import java.time.LocalTime

object ShowNotification {
    fun showMedicationNotification(context: Context) {
       NotificationUtils.showNotification(
           context = context,
           channelId = "medication_notification",
           notificationId = 1,
           channelName = "Medication Channel",
           descriptionChannel = "Alarm Medication",
           activity = SuaActivity::class.java,
           notificationTitle = "Está na hora da sua medicação",
           notificationDescription = "Clique aqui para confirmar"
       )
    }
}