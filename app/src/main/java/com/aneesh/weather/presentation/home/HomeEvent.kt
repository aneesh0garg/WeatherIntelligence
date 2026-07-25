package com.aneesh.weather.presentation.home

import com.aneesh.weather.domain.model.CitySuggestion

sealed interface HomeEvent {

    data class SearchCity(
        val city: String
    ) : HomeEvent

    data class SearchQueryChanged(
        val query: String
    ) : HomeEvent

    data class SelectCitySuggestion(
        val suggestion: CitySuggestion
    ) : HomeEvent

    data object DismissCitySuggestions : HomeEvent

    data object Refresh : HomeEvent

    data object SendTestAlert : HomeEvent

    data object ToggleFavorite : HomeEvent

    data object LoadCurrentCity : HomeEvent

    data object UseFallbackCity : HomeEvent
}
