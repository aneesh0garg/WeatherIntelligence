package com.aneesh.weather.presentation.home

sealed interface HomeEvent {

    data class SearchCity(
        val city: String
    ) : HomeEvent

    data object Refresh : HomeEvent

    data object SendTestAlert : HomeEvent

    data object ToggleFavorite : HomeEvent

    data object LoadCurrentCity : HomeEvent

    data object UseFallbackCity : HomeEvent
}
