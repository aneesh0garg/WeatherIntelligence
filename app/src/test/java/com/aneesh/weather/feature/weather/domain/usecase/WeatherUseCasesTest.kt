package com.aneesh.weather.feature.weather.domain.usecase

import com.aneesh.weather.feature.weather.domain.model.Resource
import com.aneesh.weather.feature.weather.domain.model.Weather
import com.aneesh.weather.feature.weather.domain.model.WeatherSyncResult
import com.aneesh.weather.feature.weather.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class WeatherUseCasesTest {
    @Test
    fun `get weather use case forwards force refresh`() {
        val repository = RecordingRepository()

        GetWeatherUseCase(repository)("Delhi", forceRefresh = true)

        assertEquals("Delhi", repository.requestedCity)
        assertEquals(true, repository.requestedForceRefresh)
    }

    @Test
    fun `manage favorites use case delegates add remove and selection`() = runTest {
        val repository = RecordingRepository()
        val useCase = ManageFavoritesUseCase(repository)

        useCase.add("Mumbai")
        useCase.markSelected("Mumbai")
        useCase.remove("Mumbai")

        assertEquals(listOf("add:Mumbai", "selected:Mumbai", "remove:Mumbai"), repository.actions)
    }

    private class RecordingRepository : WeatherRepository {
        var requestedCity: String? = null
        var requestedForceRefresh: Boolean? = null
        val actions = mutableListOf<String>()
        private val favorites = MutableStateFlow(emptyList<String>())

        override fun getWeather(city: String, forceRefresh: Boolean): Flow<Resource<Weather>> {
            requestedCity = city
            requestedForceRefresh = forceRefresh
            return flowOf(Resource.Loading)
        }
        override fun observeFavoriteCities(): Flow<List<String>> = favorites
        override suspend fun getLastAddedFavorite(): String? = null
        override suspend fun getLastSelectedFavorite(): String? = null
        override suspend fun markFavoriteSelected(city: String) { actions += "selected:$city" }
        override suspend fun addFavorite(city: String) { actions += "add:$city" }
        override suspend fun removeFavorite(city: String) { actions += "remove:$city" }
        override suspend fun syncFavoriteCities() = WeatherSyncResult(true, emptyList())
    }
}
