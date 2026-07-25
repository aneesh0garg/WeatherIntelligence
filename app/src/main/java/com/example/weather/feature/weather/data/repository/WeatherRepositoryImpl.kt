package com.example.weather.feature.weather.data.repository

import android.util.Log
import com.example.weather.BuildConfig
import com.example.weather.feature.weather.data.api.WeatherApi
import com.example.weather.feature.weather.data.db.WeatherDao
import com.example.weather.feature.weather.data.mapper.toDomain
import com.example.weather.feature.weather.data.mapper.toEntity
import com.example.weather.feature.weather.domain.model.Resource
import com.example.weather.feature.weather.domain.model.Weather
import com.example.weather.feature.weather.domain.repository.WeatherRepository
import com.example.weather.util.needsRefresh
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

    override fun getWeather(city: String): Flow<Resource<Weather>> = flow {
        val cached = dao.getWeather(city)

        if (cached == null || cached.needsRefresh()) {
            try {
                val response = api.getForecast(
                    apiKey = BuildConfig.WEATHER_API_KEY,
                    city = city
                )
                dao.insert(response.toEntity())
            } catch (e: Exception) {
                Log.e("Repository", "Refresh failed", e)
                if (cached == null) {
                    emit(Resource.Error(e.message ?: "Unknown Error"))
                    return@flow
                }
            }
        }

        dao.observeWeather(city)
            .map { entity ->
                if (entity != null) {
                    Resource.Success(entity.toDomain())
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
}
