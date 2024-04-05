package com.example.discoveryourself.data.room

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.discoveryourself.data.room.entities.AlarmEntity
import com.example.discoveryourself.data.room.entities.AlarmSettingsEntity
import com.example.discoveryourself.data.room.entities.ExcludedIntervalEntity
import com.example.discoveryourself.data.room.entities.MainIntervalEntity

@Database(
    entities = [AlarmSettingsEntity::class, AlarmEntity::class, MainIntervalEntity::class, ExcludedIntervalEntity::class],
    version = 3, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE alarm_settings ADD COLUMN type TEXT NOT NULL DEFAULT ''")
            }
        }

        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE IF NOT EXISTS main_interval (id INTEGER PRIMARY KEY NOT NULL, startTime TEXT NOT NULL, endTime TEXT NOT NULL, alarm_settings_id INTEGER NOT NULL, FOREIGN KEY(alarm_settings_id) REFERENCES alarm_settings(id) ON DELETE CASCADE)")

//                database.execSQL("CREATE TABLE main_interval_new (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, startTime TEXT NOT NULL, endTime TEXT NOT NULL, alarm_settings_id INTEGER NOT NULL, FOREIGN KEY(alarm_settings_id) REFERENCES alarm_settings(id) ON DELETE CASCADE)")
//
//                database.execSQL("INSERT INTO main_interval_new (id, startTime, endTime, alarm_settings_id) SELECT id, startTime, endTime, alarmSettingsId FROM main_interval")
//
//                database.execSQL("DROP TABLE main_interval")
//                database.execSQL("ALTER TABLE main_interval_new RENAME TO main_interval")

                database.execSQL("CREATE TABLE IF NOT EXISTS excluded_intervals (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, startTime TEXT NOT NULL, endTime TEXT NOT NULL, isEnabled INTEGER NOT NULL, alarm_settings_id INTEGER NOT NULL, FOREIGN KEY(alarm_settings_id) REFERENCES alarm_settings(id) ON DELETE CASCADE)")
            }
        }
    }

    abstract fun alarmDao(): AlarmDao
}
