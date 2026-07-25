package com.aneesh.weather.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Query("SELECT * FROM weather WHERE city = :city LIMIT 1")
    suspend fun getWeather(city: String): WeatherEntity?

    @Query("""
        SELECT *
        FROM weather
        WHERE city = :city
    """)
    fun observeWeather(
        city: String
    ): Flow<WeatherEntity?>

    @Query("SELECT city FROM weather")
    suspend fun getCachedCities(): List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(
        weather: WeatherEntity
    )

    @Delete
    suspend fun delete(
        weather: WeatherEntity
    )

    @Query("DELETE FROM weather")
    suspend fun clear()

}
