package com.aneesh.weather.feature.weather.domain.repository

import com.aneesh.weather.feature.weather.domain.model.Resource
import com.aneesh.weather.feature.weather.domain.model.Weather
import com.aneesh.weather.feature.weather.domain.model.WeatherSyncResult
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    fun getWeather(city: String, forceRefresh: Boolean = false): Flow<Resource<Weather>>

    /** Refreshes every city the user has already viewed and cached locally. */
    suspend fun syncCachedCities(): WeatherSyncResult

}
