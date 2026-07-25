package com.example.weather.feature.weather.data.api.model

import com.google.gson.annotations.SerializedName


data class CurrentDto(

    @SerializedName("temp_c")
    val temperature: Double,

    @SerializedName("humidity")
    val humidity: Int,

    @SerializedName("pressure_mb")
    val pressure: Double,

    @SerializedName("wind_kph")
    val wind: Double,

    @SerializedName("feelslike_c")
    val feelsLike: Double,

    @SerializedName("condition")
    val condition: ConditionDto
)