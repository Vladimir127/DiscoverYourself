package com.example.discoveryourself.app.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.discoveryourself.app.database.AlarmEntity
import com.example.discoveryourself.app.database.AlarmSettingsEntity

@Dao
interface AlarmDao {
    @Query("SELECT * FROM alarm_settings")
    fun getAllAlarmSettings(): List<AlarmSettingsEntity>

    @Query("SELECT * FROM alarms WHERE alarmSettingsId = :alarmSettingsId")
    fun getAlarmsBySettingsId(alarmSettingsId: Int): List<AlarmEntity>

    @Insert
    fun insertAlarmSettings(alarmSettings: AlarmSettingsEntity)

    @Insert
    fun insertAlarm(alarm: AlarmEntity)

    // Другие методы для обновления и удаления данных
}
