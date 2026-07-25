package com.aneesh.weather.feature.weather.data.api.model

import com.google.gson.annotations.SerializedName

data class DayDto(

    @SerializedName("maxtemp_c")
    val maxTemp: Double,

    @SerializedName("mintemp_c")
    val minTemp: Double,

    @SerializedName("avgtemp_c")
    val avgTemp: Double,

    @SerializedName("condition")
    val condition: ConditionDto
)