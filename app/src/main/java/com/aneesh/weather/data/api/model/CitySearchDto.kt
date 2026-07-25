package com.aneesh.weather.data.api.model

import com.google.gson.annotations.SerializedName

data class CitySearchDto(
    @SerializedName("name") val name: String,
    @SerializedName("region") val region: String,
    @SerializedName("country") val country: String,
    @SerializedName("lat") val latitude: Double,
    @SerializedName("lon") val longitude: Double
)
