package com.aneesh.weather.feature.weather.domain.repository

import com.aneesh.weather.feature.weather.domain.model.Resource
import com.aneesh.weather.feature.weather.domain.model.Weather
import com.aneesh.weather.feature.weather.domain.model.WeatherSyncResult
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    fun getWeather(city: String, forceRefresh: Boolean = false): Flow<Resource<Weather>>

    fun observeFavoriteCities(): Flow<List<String>>

    suspend fun getLastAddedFavorite(): String?

    suspend fun getLastSelectedFavorite(): String?

    suspend fun markFavoriteSelected(city: String)

    suspend fun addFavorite(city: String)

    suspend fun removeFavorite(city: String)

    /** Refreshes only cities the user chose to keep as favorites. */
    suspend fun syncFavoriteCities(): WeatherSyncResult

}
