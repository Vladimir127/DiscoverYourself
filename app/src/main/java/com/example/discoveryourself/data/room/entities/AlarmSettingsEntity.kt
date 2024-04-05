package com.example.discoveryourself.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarm_settings")
data class AlarmSettingsEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val count: Int,
    val volume: Int,
    val text: String,
    val type: String
)
