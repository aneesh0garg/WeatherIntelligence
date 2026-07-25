package com.example.weather.domain.model

data class DailyWeather(

    val date: String,

    val minTemp: Double,

    val maxTemp: Double,

    val condition: String,

    val icon: String
)