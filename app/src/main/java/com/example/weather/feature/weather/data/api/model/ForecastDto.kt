package com.example.weather.feature.weather.data.api.model

import com.google.gson.annotations.SerializedName


data class ForecastDto(

    @SerializedName("forecastday")
    val forecastDays: List<ForecastDayDto>
)