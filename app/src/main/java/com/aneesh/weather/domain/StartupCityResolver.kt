package com.aneesh.weather.feature.weather.domain

object StartupCityResolver {
    fun resolve(
        currentCity: String?,
        lastSelectedFavorite: String?,
        lastAddedFavorite: String?,
        defaultCity: String
    ): String = currentCity?.takeIf { it.isNotBlank() }
        ?: lastSelectedFavorite?.takeIf { it.isNotBlank() }
        ?: lastAddedFavorite?.takeIf { it.isNotBlank() }
        ?: defaultCity
}
