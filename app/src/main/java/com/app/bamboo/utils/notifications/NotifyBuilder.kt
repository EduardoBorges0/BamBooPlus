package com.app.bamboo.utils.notifications

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.app.bamboo.R


fun notifyBuilder(context: Context, title: String, text: String, actions: List<Pair<String, String>>) {
    val builder = NotificationCompat.Builder(context, "CANAL_1")
        .setContentTitle(title)
        .setContentText(text)
        .setSmallIcon(R.drawable.ic_launcher_background)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setAutoCancel(true)

    actions.forEachIndexed { index, action ->
        val intent = Intent(context, NotificationActionReceiver::class.java).apply {
            this.action = action.second
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context, index, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        builder.addAction(R.drawable.ic_launcher_background, action.first, pendingIntent)
    }

    with(NotificationManagerCompat.from(context)) {
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED) {
            return
        }
        notify(1, builder.build())
    }
}
