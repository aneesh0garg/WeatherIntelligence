package com.aneesh.weather.presentation.home

import com.aneesh.weather.domain.model.Resource
import com.aneesh.weather.domain.model.Weather
import com.aneesh.weather.domain.model.WeatherSyncResult
import com.aneesh.weather.domain.repository.WeatherRepository
import com.aneesh.weather.domain.usecase.GetWeatherUseCase
import com.aneesh.weather.domain.usecase.ManageFavoritesUseCase
import com.aneesh.weather.domain.usecase.SearchCitiesUseCase
import com.aneesh.weather.data.location.CurrentCityProvider
import com.aneesh.weather.worker.WeatherAlertNotifier
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {
    private val dispatcher = StandardTestDispatcher()

    @Before fun setUp() = Dispatchers.setMain(dispatcher)
    @After fun tearDown() = Dispatchers.resetMain()

    @Test
    fun `fallback loads last selected favorite before recent favorite`() = runTest(dispatcher) {
        val repository = FakeWeatherRepository(lastSelected = "Paris", lastAdded = "London")
        val viewModel = HomeViewModel(
            GetWeatherUseCase(repository),
            ManageFavoritesUseCase(repository),
            SearchCitiesUseCase(repository),
            mockk<WeatherAlertNotifier>(relaxed = true),
            mockk<CurrentCityProvider>(relaxed = true)
        )

        viewModel.onEvent(HomeEvent.UseFallbackCity)
        advanceUntilIdle()

        val state = viewModel.uiState.value as HomeUiState.Success
        assertEquals("Paris", state.weather.city)
    }

    private class FakeWeatherRepository(
        private val lastSelected: String?,
        private val lastAdded: String?
    ) : WeatherRepository {
        private val favorites = MutableStateFlow(emptyList<String>())

        override suspend fun searchCities(query: String) = emptyList<com.aneesh.weather.domain.model.CitySuggestion>()
        override fun getWeather(city: String, forceRefresh: Boolean): Flow<Resource<Weather>> =
            flowOf(Resource.Success(weather(city)))
        override fun observeFavoriteCities(): Flow<List<String>> = favorites
        override suspend fun getLastAddedFavorite(): String? = lastAdded
        override suspend fun getLastSelectedFavorite(): String? = lastSelected
        override suspend fun markFavoriteSelected(city: String) = Unit
        override suspend fun addFavorite(city: String) = Unit
        override suspend fun removeFavorite(city: String) = Unit
        override suspend fun syncFavoriteCities() = WeatherSyncResult(true, emptyList())

        private fun weather(city: String) = Weather(
            city = city, country = "", temperature = 20.0, feelsLike = 20.0, humidity = 50,
            pressure = 1010.0, wind = 10.0, condition = "Clear", icon = "",
            hourly = emptyList(), daily = emptyList(), updatedAt = 0, localTime = ""
        )
    }
}
