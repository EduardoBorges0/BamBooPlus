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
            date = "2024-08-16",
            time = 8
        ),
        MedicationEntities(
            medicationName = "Luftal",
            description = "To Gases",
            pillOrDrop = "Pill",
            daysOrHours = "Days",
            medicationTime = "10:00",
            date = "2024-08-25",
            time = 4
        ),
    )
    val fakeScheduleMedications = listOf(
        MedicationSchedule(
            medicationName = "Dorflex",
            medicationId = 1,
            scheduledTime = "10:00",
            date = "2024-08-16",
            intervalTime = "4",
            hoursOrDays = "Hours"
        ),
        MedicationSchedule(
            medicationName = "Luftal",
            medicationId = 2,
            scheduledTime = "10:00",
            date = "2024-08-16",
            intervalTime = "7",
            hoursOrDays = "Hours"
        ),
        MedicationSchedule(
            medicationName = "Luftal",
            medicationId = 2,
            scheduledTime = "14:00",
            date = "2024-08-16",
            intervalTime = "3",
            hoursOrDays = "Hours"
        ),
        MedicationSchedule(
            medicationName = "Luftal",
            medicationId = 2,
            scheduledTime = "18:00",
            date = "2024-08-16",
            intervalTime = "4",
            hoursOrDays = "Hours"
        ),
        MedicationSchedule(
            medicationName = "Dorflex",
            medicationId = 1,
            scheduledTime = "18:00",
            date = "2024-08-16",
            intervalTime = "4",
            hoursOrDays = "Hours"
        ),
        MedicationSchedule(
            medicationName = "Luftal",
            medicationId = 2,
            scheduledTime = "22:00",
            date = "2024-08-16",
            intervalTime = "4",
            hoursOrDays = "Hours"
        ),
        MedicationSchedule(
            medicationName = "Luftal",
            medicationId = 2,
            scheduledTime = "02:00",
            date = "2024-08-16",
            intervalTime = "4",
            hoursOrDays = "Hours"
        ),
        MedicationSchedule(
            medicationName = "Dorflex",
            medicationId = 1,
            scheduledTime = "02:00",
            date = "2024-08-16",
            intervalTime = "4",
            hoursOrDays = "Hours"
        ),
        MedicationSchedule(
            medicationName = "Luftal",
            medicationId = 2,
            scheduledTime = "06:00",
            date = "2024-08-16",
            intervalTime = "4",
            hoursOrDays = "Hours"
        ),
    )
}