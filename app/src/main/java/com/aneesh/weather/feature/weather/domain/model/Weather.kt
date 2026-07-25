package com.aneesh.weather.feature.weather.domain.model


data class Weather(

    val city: String,
    val country: String,
    val temperature: Double,
    val feelsLike: Double,
    val humidity: Int,
    val pressure: Double,
    val wind: Double,
    val condition: String,
    val icon: String,
    val hourly: List<HourlyWeather>,
    val daily: List<DailyWeather>,
    val updatedAt: Long,
    val localTime: String
)