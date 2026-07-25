package com.aneesh.weather.feature.weather.domain.model

data class SevereWeatherAlert(
    val city: String,
    val event: String,
    val headline: String,
    val description: String
)

data class WeatherSyncResult(
    val completed: Boolean,
    val severeAlerts: List<SevereWeatherAlert>
)
