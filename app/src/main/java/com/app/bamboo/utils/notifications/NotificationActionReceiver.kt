package com.app.bamboo.utils.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationActionReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null && intent != null) {
            when (intent.action) {
                "ACTION_1" -> Log.d("NotificationAction", "Botão 1 pressionado")
                "ACTION_2" -> Log.d("NotificationAction", "Botão 2 pressionado")
            }
        }
    }
}
