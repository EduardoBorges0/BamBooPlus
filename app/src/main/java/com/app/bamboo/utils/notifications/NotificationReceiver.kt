package com.app.bamboo.utils.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.app.bamboo.di.NotificationUseCasesEntryPoint
import dagger.hilt.android.EntryPointAccessors

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            val title = intent?.getStringExtra("NOTIFICATION_TITLE") ?: "Título Padrão"
            val message = intent?.getStringExtra("NOTIFICATION_MESSAGE") ?: "Mensagem Padrão"

            notifyBuilder(
                it,
                title,
                message,
                listOf("Ação 1" to "ACTION_1", "Ação 2" to "ACTION_2")
            )
        }
    }
}
