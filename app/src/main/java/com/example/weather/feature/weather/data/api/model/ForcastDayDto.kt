package com.example.weather.feature.weather.data.api.model

import com.google.gson.annotations.SerializedName

data class ForecastDayDto(

    @SerializedName("date")
    val date: String,

    @SerializedName("day")
    val day: DayDto,

    @SerializedName("hour")
    val hour: List<HourDto>
)