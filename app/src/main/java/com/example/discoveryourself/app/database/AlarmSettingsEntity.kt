package com.example.discoveryourself.app.database

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarm_settings")
data class AlarmSettingsEntity(@PrimaryKey val id: Int,
                               //val mainInterval: TimeRange,
                               val signalCountPerHour: Int,
                               val volume: Int,
                               //val signalType: SignalType,
                               val melodyUri: Uri,
                               val imageUri: Uri,
                               val text: String)
