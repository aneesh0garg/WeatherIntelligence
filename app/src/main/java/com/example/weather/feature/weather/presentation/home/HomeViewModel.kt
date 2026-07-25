package com.example.weather.feature.weather.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.feature.weather.domain.model.Resource
import com.example.weather.feature.weather.domain.model.Weather
import com.example.weather.feature.weather.domain.usecase.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        onEvent(HomeEvent.SearchCity("London"))
    }

    private fun loadWeather(city: String) {
        viewModelScope.launch {
            getWeatherUseCase(city).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _uiState.value = HomeUiState.Loading
                    }

                    is Resource.Success<*> -> {
                        val weather = resource.data as? Weather
                        if (weather != null) {
                            _uiState.value = HomeUiState.Success(
                                weather = weather,
                                isOffline = false // Assuming default for now
                            )
                        } else {
                            _uiState.value = HomeUiState.Error("Invalid weather data")
                        }
                    }

                    is Resource.Error -> {
                        _uiState.value = HomeUiState.Error(resource.message)
                    }
                }
            }
        }
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.SearchCity -> {
                loadWeather(event.city)
            }

            HomeEvent.Refresh -> {
                val currentState = _uiState.value
                if (currentState is HomeUiState.Success) {
                    loadWeather(currentState.weather.city)
                }
            }
        }
    }
}
