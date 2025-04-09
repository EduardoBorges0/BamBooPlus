package com.app.bamboo.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "medication_schedule",
    foreignKeys = [
        ForeignKey(
            entity = MedicationEntities::class,
            parentColumns = ["id"],
            childColumns = ["medication_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class MedicationSchedule(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo("medication_name") val medicationName: String,
    @ColumnInfo(name = "medication_id") val medicationId: Long,
    @ColumnInfo(name = "scheduled_time") val scheduledTime: String,
    @ColumnInfo(name = "accomplish") val accomplish: Boolean? = null
)
