package com.example.discoveryourself.domain.repository

import com.example.discoveryourself.domain.models.AlarmSettings

interface AlarmRepository {
    suspend fun getAlarmSettings(): AlarmSettings
    suspend fun saveAlarmSettings(alarmSettings: AlarmSettings)
}