package com.example.discoveryourself.data.repositories

import com.example.discoveryourself.data.entities.AlarmSettingsEntity
import com.example.discoveryourself.data.room.AlarmDao
import com.example.discoveryourself.domain.models.AlarmSettings

class RoomAlarmRepositoryImpl(private val alarmDao: AlarmDao) :
    AlarmRepository {
    fun getAlarmSettings(): AlarmSettings {
        val alarmSettingsEntity = alarmDao.getAlarmSettings()

        return alarmSettingsEntity?.toAlarmSettings() ?: getDefaultAlarmSettings()
    }

    fun saveAlarmSettings(alarmSettings: AlarmSettings) {
        alarmDao.insertAlarmSettings(alarmSettings.toAlarmSettingsEntity())
    }

    private fun AlarmSettingsEntity.toAlarmSettings(): AlarmSettings {
        return AlarmSettings(
            id = this.id,
            count = this.count,
            text = this.text,
            volume = this.volume,
            changed = false
        )
    }

    private fun AlarmSettings.toAlarmSettingsEntity(): AlarmSettingsEntity {
        return AlarmSettingsEntity(
            id = this.id,
            count = this.count,
            text = this.text,
            volume = this.volume
        )
    }

    private fun getDefaultAlarmSettings(): AlarmSettings {
        return AlarmSettings(
            id = 1,
            count = 5,
            text = "Default text",
            volume = 50,
            changed = false
        )
    }
}