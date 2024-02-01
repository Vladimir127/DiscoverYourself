package com.example.discoveryourself.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarm_settings")
data class AlarmSettingsEntity(@PrimaryKey val id: Int,
                               val count: Int,
                               val volume: Int,
                               val text: String)
