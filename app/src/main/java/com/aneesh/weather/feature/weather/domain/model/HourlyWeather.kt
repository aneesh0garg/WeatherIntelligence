package com.aneesh.weather.feature.weather.domain.model

data class HourlyWeather(

    val time: String,

    val temperature: Double,

    val condition: String,

    val icon: String
)