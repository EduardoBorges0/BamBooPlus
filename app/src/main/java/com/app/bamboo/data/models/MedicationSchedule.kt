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
            onDelete = ForeignKey.CASCADE // Garante que os agendamentos sejam removidos ao deletar uma medicação
        )
    ]
)
data class MedicationSchedule(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "medication_id") val medicationId: Long,
    @ColumnInfo(name = "scheduled_time") val scheduledTime: String
)
