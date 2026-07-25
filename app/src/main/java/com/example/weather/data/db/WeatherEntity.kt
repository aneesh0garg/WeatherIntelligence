package com.example.weather.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weather.domain.model.DailyWeather
import com.example.weather.domain.model.HourlyWeather

@Entity(tableName = "weather")
data class WeatherEntity(

    @PrimaryKey
    val city: String,

    val country: String,

    val temperature: Double,

    val feelsLike: Double,

    val humidity: Int,

    val pressure: Double,

    val wind: Double,

    val condition: String,

    val icon: String,

    val hourlyForecast: List<HourlyWeather>,

    val dailyForecast: List<DailyWeather>,

    val updatedAt: Long
)