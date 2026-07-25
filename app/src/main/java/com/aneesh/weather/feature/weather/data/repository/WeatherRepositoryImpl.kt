package com.aneesh.weather.feature.weather.data.repository

import android.util.Log
import com.aneesh.weather.BuildConfig
import com.aneesh.weather.feature.weather.data.api.WeatherApi
import com.aneesh.weather.feature.weather.data.db.WeatherDao
import com.aneesh.weather.feature.weather.data.mapper.toDomain
import com.aneesh.weather.feature.weather.data.mapper.toEntity
import com.aneesh.weather.feature.weather.domain.model.Resource
import com.aneesh.weather.feature.weather.domain.model.Weather
import com.aneesh.weather.feature.weather.domain.repository.WeatherRepository
import com.aneesh.weather.util.needsRefresh
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi,
    private val dao: WeatherDao
) : WeatherRepository {

    override suspend fun syncCachedCities(): Boolean {
        val cities = dao.getCachedCities()
        if (cities.isEmpty()) return true

        return cities.all { city -> runCatching { refresh(city) }.isSuccess }
    }

    override fun getWeather(city: String, forceRefresh: Boolean): Flow<Resource<Weather>> = flow {
        val normalizedCity = city.trim()
        if (normalizedCity.isBlank()) {
            emit(Resource.Error("Enter a city name"))
            return@flow
        }

        val cached = dao.getWeather(normalizedCity)
        var refreshFailed = false

        if (forceRefresh || cached == null || cached.needsRefresh()) {
            try {
                refresh(normalizedCity)
            } catch (e: Exception) {
                Log.e("Repository", "Refresh failed", e)
                if (cached == null) {
                    emit(Resource.Error(e.message ?: "Unknown Error"))
                    return@flow
                }
                refreshFailed = true
            }
        }

        dao.observeWeather(normalizedCity)
            .map { entity ->
                if (entity != null) {
                    Resource.Success(entity.toDomain(), isStale = refreshFailed)
                } else {
                    Resource.Error("City not found")
                }
            }
            .collect {
                emit(it)
            }
    }.onStart {
        emit(Resource.Loading)
    }.catch { e ->
        emit(Resource.Error(e.message ?: "Unknown Error"))
    }

    private suspend fun refresh(city: String) {
        val response = api.getForecast(
            apiKey = BuildConfig.WEATHER_API_KEY,
            city = city
        )
        dao.insert(response.toEntity())
    }
}
