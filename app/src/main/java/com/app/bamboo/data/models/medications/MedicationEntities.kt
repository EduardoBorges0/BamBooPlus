package com.app.bamboo.data.models.medications

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medication_entity")
data class MedicationEntities(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "medication_name")
    val medicationName: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "pill_or_drop")
    val pillOrDrop: String,
    @ColumnInfo(name = "amount_medication")
    val amountMedication: Int,
    @ColumnInfo(name = "quantity")
    val quantity: Int,
    @ColumnInfo(name = "quantityThreshold")
    val quantityThreshold: Int,
    @ColumnInfo(name = "days_or_hours")
    val daysOrHours: String,
    @ColumnInfo(name = "medication_time")
    val medicationTime: String,
    @ColumnInfo(name = "date")
    val date: String,
    val time: Long,
    @ColumnInfo(name = "accomplish")
    val accomplish: Boolean? = null
)