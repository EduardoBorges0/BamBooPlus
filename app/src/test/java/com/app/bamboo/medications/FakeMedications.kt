package com.app.bamboo.medications

import com.app.bamboo.data.models.MedicationEntities

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
            time = 2
        ),
    )
}