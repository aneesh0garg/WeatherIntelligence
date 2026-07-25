package com.aneesh.weather.feature.weather.presentation.home

sealed interface HomeEvent {

    data class SearchCity(
        val city: String
    ) : HomeEvent

    data object Refresh : HomeEvent
}