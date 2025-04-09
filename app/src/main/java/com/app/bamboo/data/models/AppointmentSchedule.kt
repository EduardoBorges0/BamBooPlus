package com.app.bamboo.data.models

import androidx.room.ColumnInfo

data class AppointmentSummary(
    @ColumnInfo(name = "appointment_type")
    val appointmentType: String,

    @ColumnInfo(name = "appointment_date")
    val appointmentDate: String,

    @ColumnInfo(name = "appointment_time")
    val appointmentTime: String
)
