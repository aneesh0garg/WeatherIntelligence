package com.aneesh.weather.feature.weather.data.db

import androidx.room.Database
import androidx.room.migration.Migration
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.room.TypeConverters
import com.aneesh.weather.feature.weather.data.db.converter.WeatherTypeConverters
@Database(
    entities = [WeatherEntity::class, FavoriteCityEntity::class],
    version = 3,
    exportSchema = false
)
@TypeConverters(
    WeatherTypeConverters::class
)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
    abstract fun favoriteCityDao(): FavoriteCityDao

    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    "CREATE TABLE IF NOT EXISTS `favorite_cities` (`city` TEXT NOT NULL, `addedAt` INTEGER NOT NULL, PRIMARY KEY(`city`))"
                )
            }
        }

        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    "ALTER TABLE `favorite_cities` ADD COLUMN `lastSelectedAt` INTEGER NOT NULL DEFAULT 0"
                )
            }
        }
    }
}
