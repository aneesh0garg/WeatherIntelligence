package com.example.weather.feature.weather.presentation.home

import com.example.weather.feature.weather.domain.model.Weather

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Success(val weather: Weather, val isOffline: Boolean) : HomeUiState
    data class Error(val message: String) : HomeUiState
}