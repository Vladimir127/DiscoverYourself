package com.example.discoveryourself.infrastructure

import android.app.Application
import androidx.room.Room
import com.example.discoveryourself.data.repositories.RoomAlarmRepositoryImpl
import com.example.discoveryourself.data.room.AppDatabase

class MyApp : Application() {
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "alarm-db"
        ).allowMainThreadQueries().build()
    }

    private val alarmDao by lazy {
        db.alarmDao()
    }

    val repository by lazy {
        RoomAlarmRepositoryImpl(alarmDao)
    }
}