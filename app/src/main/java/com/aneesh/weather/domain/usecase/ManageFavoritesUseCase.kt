package com.aneesh.weather.feature.weather.domain.usecase

import com.aneesh.weather.feature.weather.domain.repository.WeatherRepository
import javax.inject.Inject

class ManageFavoritesUseCase @Inject constructor(
    private val repository: WeatherRepository
) {
    fun observe() = repository.observeFavoriteCities()

    suspend fun lastAdded() = repository.getLastAddedFavorite()

    suspend fun lastSelected() = repository.getLastSelectedFavorite()

    suspend fun markSelected(city: String) = repository.markFavoriteSelected(city)

    suspend fun add(city: String) = repository.addFavorite(city)

    suspend fun remove(city: String) = repository.removeFavorite(city)
}
