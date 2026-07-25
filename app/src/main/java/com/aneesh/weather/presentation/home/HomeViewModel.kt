package com.aneesh.weather.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aneesh.weather.domain.StartupCityResolver
import com.aneesh.weather.domain.model.Resource
import com.aneesh.weather.domain.model.SevereWeatherAlert
import com.aneesh.weather.domain.model.Weather
import com.aneesh.weather.domain.usecase.GetWeatherUseCase
import com.aneesh.weather.domain.usecase.ManageFavoritesUseCase
import com.aneesh.weather.location.CurrentCityProvider
import com.aneesh.weather.worker.WeatherAlertNotifier
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val manageFavoritesUseCase: ManageFavoritesUseCase,
    private val weatherAlertNotifier: WeatherAlertNotifier,
    private val currentCityProvider: CurrentCityProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState = _uiState.asStateFlow()
    private val _favoriteCities = MutableStateFlow<List<String>>(emptyList())
    val favoriteCities = _favoriteCities.asStateFlow()
    private val _effects = MutableSharedFlow<HomeEffect>()
    val effects = _effects.asSharedFlow()
    private val _needsInitialLocation = MutableStateFlow(false)
    val needsInitialLocation = _needsInitialLocation.asStateFlow()
    private var weatherJob: Job? = null

    init {
        viewModelScope.launch {
            manageFavoritesUseCase.observe().collect { _favoriteCities.value = it }
        }
        _needsInitialLocation.value = true
    }

    private fun loadWeather(city: String, forceRefresh: Boolean = false) {
        weatherJob?.cancel()
        weatherJob = viewModelScope.launch {
            getWeatherUseCase(city, forceRefresh).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        if (_uiState.value !is HomeUiState.Success) {
                            _uiState.value = HomeUiState.Loading
                        }
                    }

                    is Resource.Success<*> -> {
                        val weather = resource.data as? Weather
                        if (weather != null) {
                            _uiState.value = HomeUiState.Success(
                                weather = weather,
                                isOffline = resource.isStale
                            )
                        } else {
                            _uiState.value = HomeUiState.Error("Invalid weather data")
                        }
                    }

                    is Resource.Error -> {
                        if (_uiState.value is HomeUiState.Success) {
                            _effects.emit(HomeEffect.ShowToast(resource.message))
                        } else {
                            _uiState.value = HomeUiState.Error(resource.message)
                        }
                    }
                }
            }
        }
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.SearchCity -> {
                viewModelScope.launch {
                    if (_favoriteCities.value.any { it.equals(event.city, ignoreCase = true) }) {
                        manageFavoritesUseCase.markSelected(event.city)
                    }
                }
                loadWeather(event.city)
            }

            HomeEvent.Refresh -> {
                val currentState = _uiState.value
                if (currentState is HomeUiState.Success) {
                    loadWeather(currentState.weather.city, forceRefresh = true)
                }
            }

            HomeEvent.SendTestAlert -> {
                weatherAlertNotifier.notify(
                    SevereWeatherAlert(
                        city = "Test City",
                        event = "Severe storm warning",
                        headline = "This is a test weather notification",
                        description = "If you can read this, severe-weather notifications are enabled."
                    )
                )
            }

            HomeEvent.ToggleFavorite -> {
                val state = _uiState.value as? HomeUiState.Success ?: return
                viewModelScope.launch {
                    val city = state.weather.city
                    if (_favoriteCities.value.any { it.equals(city, ignoreCase = true) }) {
                        manageFavoritesUseCase.remove(city)
                    } else {
                        manageFavoritesUseCase.add(city)
                    }
                }
            }

            HomeEvent.LoadCurrentCity -> {
                _needsInitialLocation.value = false
                viewModelScope.launch {
                    val city = currentCityProvider.getCity()
                    loadWeather(
                        StartupCityResolver.resolve(
                            currentCity = city,
                            lastSelectedFavorite = manageFavoritesUseCase.lastSelected(),
                            lastAddedFavorite = manageFavoritesUseCase.lastAdded(),
                            defaultCity = DEFAULT_CITY
                        )
                    )
                }
            }

            HomeEvent.UseFallbackCity -> {
                _needsInitialLocation.value = false
                loadRecentFavoriteOrDefault()
            }
        }
    }

    private fun loadRecentFavoriteOrDefault() {
        viewModelScope.launch {
            loadWeather(
                manageFavoritesUseCase.lastSelected()
                    ?: manageFavoritesUseCase.lastAdded()
                    ?: DEFAULT_CITY
            )
        }
    }

    private companion object {
        const val DEFAULT_CITY = "London"
    }
}
