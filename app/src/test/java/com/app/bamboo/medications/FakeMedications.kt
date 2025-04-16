package com.app.bamboo.medications

import com.app.bamboo.data.models.medications.MedicationEntities
import com.app.bamboo.data.models.medications.MedicationSchedule

object FakeMedications {
    val fakeMedications = listOf(
        MedicationEntities(
            medicationName = "Dorflex",
            description = "To Pain",
            pillOrDrop = "Pill",
            daysOrHours = "Hours",
            medicationTime = "10:00",
            time = 8
        ),
        MedicationEntities(
            medicationName = "Luftal",
            description = "To Gases",
            pillOrDrop = "Pill",
            daysOrHours = "Days",
            medicationTime = "10:00",
            time = 4
        ),
    )
    val fakeScheduleMedications = listOf(
        MedicationSchedule(
            medicationName = "Dorflex",
            medicationId = 1,
            scheduledTime = "10:00",
        ),
        MedicationSchedule(
            medicationName = "Luftal",
            medicationId = 2,
            scheduledTime = "10:00",
        ),
        MedicationSchedule(
            medicationName = "Luftal",
            medicationId = 2,
            scheduledTime = "14:00",
        ),
        MedicationSchedule(
            medicationName = "Luftal",
            medicationId = 2,
            scheduledTime = "18:00",
        ),
        MedicationSchedule(
            medicationName = "Dorflex",
            medicationId = 1,
            scheduledTime = "18:00",
        ),
        MedicationSchedule(
            medicationName = "Luftal",
            medicationId = 2,
            scheduledTime = "22:00",
        ),
        MedicationSchedule(
            medicationName = "Luftal",
            medicationId = 2,
            scheduledTime = "02:00",
        ),
        MedicationSchedule(
            medicationName = "Dorflex",
            medicationId = 1,
            scheduledTime = "02:00",
        ),
        MedicationSchedule(
            medicationName = "Luftal",
            medicationId = 2,
            scheduledTime = "06:00",
        ),
    )
}