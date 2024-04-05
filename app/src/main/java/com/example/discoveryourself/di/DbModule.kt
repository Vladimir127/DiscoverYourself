package com.example.discoveryourself.di

import android.content.Context
import androidx.room.Room
import com.example.discoveryourself.data.room.AlarmDao
import com.example.discoveryourself.data.room.AppDatabase
import com.example.discoveryourself.data.room.RoomAlarmRepositoryImpl
import com.example.discoveryourself.domain.repository.AlarmRepository
import dagger.Module
import dagger.Provides

@Module
class DbModule(private val context: Context) {

    @Provides
    fun provideContext(): Context {
        return context
    }

    @Provides
    fun provideDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, "alarm-db"
        )
            .addMigrations(AppDatabase.MIGRATION_1_2)
            .addMigrations(AppDatabase.MIGRATION_2_3)
            .build()
    }

    @Provides
    fun provideDao(database: AppDatabase): AlarmDao {
        return database.alarmDao()
    }

    @Provides
    fun provideAlarmRepository(alarmDao: AlarmDao): AlarmRepository {
        return RoomAlarmRepositoryImpl(alarmDao)
    }
}