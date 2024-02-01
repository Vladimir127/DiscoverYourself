package com.example.discoveryourself.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarms")
data class AlarmEntity(
    @PrimaryKey val id: Int,
    val alarmSettingsId: Int,
    val time: Long,
    //val melodyUri: Uri,
    //val imageUri: Uri,
    val text: String
)
