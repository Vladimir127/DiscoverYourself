package com.example.discoveryourself.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.discoveryourself.data.room.entities.AlarmEntity
import com.example.discoveryourself.data.room.entities.AlarmSettingsEntity
import com.example.discoveryourself.data.room.entities.ExcludedIntervalEntity
import com.example.discoveryourself.data.room.entities.MainIntervalEntity

@Dao
interface AlarmDao {
    @Query("SELECT * FROM alarm_settings LIMIT 1")
    fun getAlarmSettings(): AlarmSettingsEntity?

    @Query("SELECT * FROM alarm_settings WHERE id = :id")
    suspend fun getAlarmSettingsById(id: Long): AlarmSettingsEntity?

    @Query("SELECT * FROM main_interval WHERE alarm_settings_id = :alarmSettingsId")
    suspend fun getMainIntervalByAlarmSettingsId(alarmSettingsId: Long): MainIntervalEntity?

    @Query("SELECT * FROM excluded_intervals WHERE alarm_settings_id = :alarmSettingsId")
    suspend fun getExcludedIntervalsByAlarmSettingsId(alarmSettingsId: Long): List<ExcludedIntervalEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAlarmSettings(alarmSettings: AlarmSettingsEntity): Long

    @Insert
    suspend fun insertMainInterval(mainIntervalEntity: MainIntervalEntity)

    @Insert
    suspend fun insertExcludedInterval(excludedIntervalEntity: ExcludedIntervalEntity)

    @Update
    fun updateAlarmSettings(alarmSettings: AlarmSettingsEntity)

    @Update
    suspend fun updateMainInterval(mainIntervalEntity: MainIntervalEntity)

    @Update
    suspend fun updateExcludedInterval(excludedIntervalEntity: ExcludedIntervalEntity)
}
