package com.aneesh.weather.data.api.model

import com.google.gson.annotations.SerializedName


data class HourDto(

    @SerializedName("time")
    val time: String,

    @SerializedName("temp_c")
    val temperature: Double,

    @SerializedName("condition")
    val condition: ConditionDto,

    @SerializedName("chance_of_rain")
    val chanceOfRain: Int = 0
)
