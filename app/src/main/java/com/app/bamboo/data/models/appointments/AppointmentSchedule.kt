package com.app.bamboo.data.models.appointments

import androidx.room.ColumnInfo

data class AppointmentSummary(
    val id: Long,

    @ColumnInfo(name = "appointment_type")
    val appointmentType: String,

    @ColumnInfo(name = "appointment_date")
    val appointmentDate: String,

    @ColumnInfo(name = "appointment_time")
    val appointmentTime: String
)
