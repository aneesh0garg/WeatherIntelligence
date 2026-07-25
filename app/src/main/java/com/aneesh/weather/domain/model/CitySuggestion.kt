package com.aneesh.weather.domain.model

data class CitySuggestion(
    val city: String,
    val region: String = "",
    val country: String = "",
    val latitude: Double? = null,
    val longitude: Double? = null
) {
    val subtitle: String
        get() = listOf(region, country).filter { it.isNotBlank() }.joinToString(", ")
}
