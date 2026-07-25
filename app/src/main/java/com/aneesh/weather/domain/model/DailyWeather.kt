package com.aneesh.weather.domain.model

data class DailyWeather(

    val date: String,

    val minTemp: Double,

    val maxTemp: Double,

    val condition: String,

    val icon: String,

    val chanceOfRain: Int = 0
)
