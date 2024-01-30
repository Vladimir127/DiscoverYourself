package com.example.discoveryourself.app.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [AlarmSettingsEntity::class, AlarmEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun alarmDao(): AlarmDao
}
