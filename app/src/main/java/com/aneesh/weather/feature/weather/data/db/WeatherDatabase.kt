package com.aneesh.weather.feature.weather.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aneesh.weather.feature.weather.data.db.converter.WeatherTypeConverters

private const val CACHE_TTL = 30 * 60 * 1000L

fun shouldRefresh(entity: WeatherEntity?): Boolean {
    if (entity == null) return true
    return System.currentTimeMillis() - entity.updatedAt > CACHE_TTL
}

@Database(
    entities = [WeatherEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    WeatherTypeConverters::class
)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}