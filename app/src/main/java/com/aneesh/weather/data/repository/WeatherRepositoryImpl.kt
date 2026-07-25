package com.aneesh.weather.data.repository

import android.util.Log
import com.aneesh.weather.BuildConfig
import com.aneesh.weather.data.api.WeatherApi
import com.aneesh.weather.data.api.model.WeatherResponse
import com.aneesh.weather.data.db.FavoriteCityDao
import com.aneesh.weather.data.db.FavoriteCityEntity
import com.aneesh.weather.data.db.WeatherDao
import com.aneesh.weather.data.mapper.toDomain
import com.aneesh.weather.data.mapper.toEntity
import com.aneesh.weather.domain.SevereAlertPolicy
import com.aneesh.weather.domain.model.Resource
import com.aneesh.weather.domain.model.SevereWeatherAlert
import com.aneesh.weather.domain.model.Weather
import com.aneesh.weather.domain.model.WeatherSyncResult
import com.aneesh.weather.domain.repository.WeatherRepository
import com.aneesh.weather.util.needsRefresh
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject
import javax.inject.Singleton
import retrofit2.HttpException

@Singleton
class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi,
    private val dao: WeatherDao,
    private val favoriteCityDao: FavoriteCityDao
) : WeatherRepository {

    override suspend fun searchCities(query: String) = api.searchCities(
        apiKey = BuildConfig.WEATHER_API_KEY,
        query = query.trim()
    ).map { it.toDomain() }

    override fun observeFavoriteCities(): Flow<List<String>> =
        favoriteCityDao.observeAll().map { favorites -> favorites.map { it.city } }

    override suspend fun getLastAddedFavorite(): String? =
        favoriteCityDao.getCities().firstOrNull()

    override suspend fun getLastSelectedFavorite(): String? =
        favoriteCityDao.getLastSelectedCity()

    override suspend fun markFavoriteSelected(city: String) {
        favoriteCityDao.markSelected(city, System.currentTimeMillis())
    }

    override suspend fun addFavorite(city: String) {
        favoriteCityDao.insert(FavoriteCityEntity(city = city.trim()))
    }

    override suspend fun removeFavorite(city: String) {
        favoriteCityDao.delete(FavoriteCityEntity(city = city))
    }

    override suspend fun syncFavoriteCities(): WeatherSyncResult {
        val cities = favoriteCityDao.getCities()
        if (cities.isEmpty()) return WeatherSyncResult(completed = true, severeAlerts = emptyList())

        var completed = true
        val alerts = cities.flatMap { city ->
            runCatching { refresh(city).toSevereAlerts(city) }
                .getOrElse {
                    completed = false
                    emptyList()
                }
        }
        return WeatherSyncResult(completed = completed, severeAlerts = alerts)
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
                    emit(Resource.Error(e.toWeatherMessage(normalizedCity)))
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

    private suspend fun refresh(city: String): WeatherResponse {
        val response = api.getForecast(
            apiKey = BuildConfig.WEATHER_API_KEY,
            city = city
        )
        dao.insert(response.toEntity())
        return response
    }

    private fun WeatherResponse.toSevereAlerts(
        city: String
    ): List<SevereWeatherAlert> = alerts?.alerts.orEmpty()
        .filter { SevereAlertPolicy.shouldNotify(it.severity) }
        .map {
            SevereWeatherAlert(
                city = city,
                event = it.event,
                headline = it.headline.ifBlank { it.event },
                description = it.description
            )
        }

    private fun Throwable.toWeatherMessage(city: String): String = when {
        this is HttpException && code() == 400 -> "We couldn't find \"$city\"."
        else -> message ?: "Unable to load weather right now."
    }
}
