package com.example.discoveryourself.data.room

import com.example.discoveryourself.data.room.entities.AlarmSettingsEntity
import com.example.discoveryourself.data.room.entities.ExcludedIntervalEntity
import com.example.discoveryourself.data.room.entities.MainIntervalEntity
import com.example.discoveryourself.domain.models.AlarmSettings
import com.example.discoveryourself.domain.models.Interval
import com.example.discoveryourself.domain.repository.AlarmRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoomAlarmRepositoryImpl(private val alarmDao: AlarmDao) :
    AlarmRepository {
    override suspend fun getAlarmSettings(): AlarmSettings {
        return withContext(Dispatchers.IO) {
            val alarmSettingsEntity = alarmDao.getAlarmSettings()

            val mainIntervalEntity =
                alarmSettingsEntity?.let { alarmDao.getMainIntervalByAlarmSettingsId(it.id) }

            val excludedIntervalEntities =
                alarmSettingsEntity?.let { alarmDao.getExcludedIntervalsByAlarmSettingsId(it.id) }

            var alarmSettings = alarmSettingsEntity?.toAlarmSettings()
            if (alarmSettings == null) {
                alarmSettings = getDefaultAlarmSettings()
            } else {
                alarmSettings.interval = mainIntervalEntity?.toInterval() ?: getDefaultInterval()
                val excludedIntervals =
                    excludedIntervalEntities?.map { it.toInterval() } ?: emptyList()
                alarmSettings.excludedIntervals = excludedIntervals
            }

            alarmSettings
        }
    }

    override suspend fun saveAlarmSettings(alarmSettings: AlarmSettings) {
        withContext(Dispatchers.IO) {
            val alarmSettingsEntity = alarmDao.getAlarmSettingsById(alarmSettings.id)

            if (alarmSettingsEntity == null) {
                insertAlarmSettings(alarmSettings)
            } else {
                updateAlarmSettings(alarmSettings)
            }
        }
    }

    private suspend fun insertAlarmSettings(alarmSettings: AlarmSettings) {
        val (alarmSettingsEntity, mainIntervalEntity, excludedIntervalEntities) = alarmSettings.toAlarmSettingsEntity()

        val alarmSettingsId = alarmDao.insertAlarmSettings(alarmSettingsEntity)

        mainIntervalEntity.alarmSettingsId = alarmSettingsId
        alarmDao.insertMainInterval(mainIntervalEntity)

        excludedIntervalEntities.forEach {
            it.alarmSettingsId = alarmSettingsId
            alarmDao.insertExcludedInterval(it)
        }
    }

private suspend fun updateAlarmSettings(alarmSettings: AlarmSettings) {
    val (alarmSettingsEntity, mainIntervalEntity, excludedIntervalEntities) = alarmSettings.toAlarmSettingsEntity()

    alarmDao.updateAlarmSettings(alarmSettingsEntity)
    alarmDao.updateMainInterval(mainIntervalEntity)

    excludedIntervalEntities.forEach {
        alarmDao.updateExcludedInterval(it)
    }
}

    private fun getDefaultInterval(): Interval {
        return Interval(id = 0, "0900", "2100", true)
    }

    private fun AlarmSettingsEntity.toAlarmSettings(): AlarmSettings {
        return AlarmSettings(
            id = this.id,
            count = this.count,
            volume = this.volume,
            text = this.text,
            type = this.type,
            interval = getDefaultInterval()
        )
    }

    private fun AlarmSettings.toAlarmSettingsEntity(): Triple<AlarmSettingsEntity, MainIntervalEntity, List<ExcludedIntervalEntity>> {
        val alarmSettingsEntity = AlarmSettingsEntity(
            id = this.id,
            count = this.count,
            text = this.text,
            volume = this.volume,
            type = this.type
        )

        val mainIntervalEntity = MainIntervalEntity(
            id = this.interval.id,
            startTime = this.interval.startTime,
            endTime = this.interval.endTime,
            alarmSettingsId = this.id
        )

        val excludedIntervalEntities = this.excludedIntervals.map {
            ExcludedIntervalEntity(
                id = it.id,
                startTime = it.startTime,
                endTime = it.endTime,
                isEnabled = it.isEnabled,
                alarmSettingsId = this.id
            )
        }

        return Triple(alarmSettingsEntity, mainIntervalEntity, excludedIntervalEntities)
    }

    private fun getDefaultAlarmSettings(): AlarmSettings {
        return AlarmSettings(
            id = 1,
            count = 5,
            volume = 50,
            text = "Default text",
            type = "type1",
            getDefaultInterval()
        )
    }

    private fun MainIntervalEntity.toInterval(): Interval {
        return Interval(id = this.id, startTime = this.startTime, endTime = this.endTime, isEnabled = true)
    }

    private fun ExcludedIntervalEntity.toInterval(): Interval {
        return Interval(
            id = this.id,
            startTime = this.startTime,
            endTime = this.endTime,
            isEnabled = this.isEnabled
        )
    }
}