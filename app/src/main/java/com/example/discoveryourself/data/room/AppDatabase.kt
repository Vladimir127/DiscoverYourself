package com.example.discoveryourself.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.discoveryourself.data.entities.AlarmEntity
import com.example.discoveryourself.data.entities.AlarmSettingsEntity

@Database(entities = [AlarmSettingsEntity::class, AlarmEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun alarmDao(): AlarmDao
}
