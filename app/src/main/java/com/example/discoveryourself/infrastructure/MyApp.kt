package com.example.discoveryourself.infrastructure

import android.app.Application
import androidx.room.Room
import com.example.discoveryourself.data.room.RoomAlarmRepositoryImpl
import com.example.discoveryourself.data.room.AppDatabase
import com.example.discoveryourself.di.AppComponent
import com.example.discoveryourself.di.DaggerAppComponent
import com.example.discoveryourself.di.DbModule

class MyApp : Application() {
    /*private val db by lazy {
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
    }*/

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .dbModule(DbModule(this))
            .build()
    }
}