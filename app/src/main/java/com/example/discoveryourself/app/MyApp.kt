package com.example.discoveryourself.app

import android.app.Application
import androidx.room.Room
import com.example.discoveryourself.app.database.AppDatabase

class MyApp : Application() {
    val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "alarm-db"
        ).build()
    }
}