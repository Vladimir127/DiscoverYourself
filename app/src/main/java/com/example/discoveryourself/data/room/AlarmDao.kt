package com.example.discoveryourself.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.discoveryourself.data.entities.AlarmEntity
import com.example.discoveryourself.data.entities.AlarmSettingsEntity

@Dao
interface AlarmDao {
    @Query("SELECT * FROM alarm_settings LIMIT 1")
    fun getAlarmSettings(): AlarmSettingsEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAlarmSettings(alarmSettings: AlarmSettingsEntity)
}
