package com.aneesh.weather.feature.weather.data.api.model

import com.google.gson.annotations.SerializedName

data class AlertsDto(
    @SerializedName("alert") val alerts: List<AlertDto> = emptyList()
)

data class AlertDto(
    @SerializedName("event") val event: String = "Weather alert",
    @SerializedName("headline") val headline: String = "",
    @SerializedName("severity") val severity: String = "",
    @SerializedName("desc") val description: String = ""
)
