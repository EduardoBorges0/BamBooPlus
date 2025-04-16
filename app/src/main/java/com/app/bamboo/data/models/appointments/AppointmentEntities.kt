package com.app.bamboo.data.models.appointments

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "appointment_entity")
data class AppointmentEntities(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "appointment_type")
    val appointmentType: String,
    @ColumnInfo(name = "online_or_onsite")
    val onlineOrOnSite: String,
    @ColumnInfo(name = "doctor_name")
    val doctorName: String,
    @ColumnInfo(name = "hospital_name")
    val hospitalName: String,
    @ColumnInfo(name = "appointment_date")
    val appointmentDate: String,
    @ColumnInfo(name = "appointment_time")
    val appointmentTime: String,
    @ColumnInfo(name = "accomplish")
    val accomplish: Boolean? = null,
)