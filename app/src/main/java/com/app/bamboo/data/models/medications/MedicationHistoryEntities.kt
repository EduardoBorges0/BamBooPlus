package com.app.bamboo.data.models.medications

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medication_history")
data class MedicationHistoryEntities (
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "medication_id") val medicationId: Long,
    @ColumnInfo(name = "medication_name") val medicationName: String,
    @ColumnInfo(name = "medication_time") val medicationTime: String,
    @ColumnInfo(name = "day_of_week") val dayOfWeek: String
)