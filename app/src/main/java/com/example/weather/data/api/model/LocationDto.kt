package com.example.weather.data.api.model

import com.google.gson.annotations.SerializedName


data class LocationDto(

    @SerializedName("name")
    val name: String,

    @SerializedName("country")
    val country: String,

    @SerializedName("localtime")
    val localTime: String
)