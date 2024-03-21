package com.example.discoveryourself.data.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "excluded_intervals",
    foreignKeys = [ForeignKey(
        entity = AlarmSettingsEntity::class,
        parentColumns = ["id"],
        childColumns = ["alarm_settings_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class ExcludedIntervalEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val startTime: String,
    val endTime: String,
    val isEnabled: Boolean,
    @ColumnInfo(name = "alarm_settings_id") var alarmSettingsId: Long = 0
)
