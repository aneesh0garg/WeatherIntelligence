package com.aneesh.weather.feature.weather.data.api.model

import com.google.gson.annotations.SerializedName

data class WeatherResponse(

    @SerializedName("location")
    val location: LocationDto,

    @SerializedName("current")
    val current: CurrentDto,

    @SerializedName("forecast")
    val forecast: ForecastDto,

    @SerializedName("alerts")
    val alerts: AlertsDto? = null
)
