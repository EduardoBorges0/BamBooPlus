package com.app.bamboo.presentation.view.stateClass

data class MedicationBasicState(
    val name: String = "",
    val function: String = "",
    val stock: Long = 0L,
    val warningStock: Int = 0
)

// Para Tela 2
data class MedicationPillOrDropState(
    val pillOrDrop: String = "",
    val quantity: Int = 0
)

// Para Tela 3
data class MedicationScheduleState(
    val startDate: String = "",
    val startTime: String = "",
    val intervalType: String = "", // horas ou dias
    val intervalValue: Long = 0L,
)
