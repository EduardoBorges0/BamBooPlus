package com.app.bamboo.domain.usecases

import android.content.Context
import com.app.bamboo.utils.notifications.scheduleNotification
import java.time.LocalTime
import javax.inject.Inject

class ScheduleNextNotificationUseCase @Inject constructor() {
    operator fun invoke(
        context: Context,
        medications: List<String>,
        title: String?,
        message: String?,
    ) {
        val now = LocalTime.now()

        // Converte a lista de horários para LocalTime
        val times = medications.map { LocalTime.parse(it) }

        // Encontra o primeiro horário futuro
        val nextTime = times.firstOrNull { it.isAfter(now) }
            ?: times.firstOrNull()
                ?.plusHours(24) // Se não houver horário futuro, agenda para o próximo dia

        // Agendar notificação se um horário válido for encontrado
        nextTime?.let {
            scheduleNotification(context, it.hour, it.minute, title.toString(), message.toString())
        }
    }
}
