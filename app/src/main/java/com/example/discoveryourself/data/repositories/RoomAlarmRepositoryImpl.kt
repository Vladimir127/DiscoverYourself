package com.example.discoveryourself.data.repositories

import android.os.Handler
import com.example.discoveryourself.app.database.AlarmDao

class RoomAlarmRepositoryImpl(private val dao: AlarmDao, private val handler: Handler) :
    AlarmRepository {
}