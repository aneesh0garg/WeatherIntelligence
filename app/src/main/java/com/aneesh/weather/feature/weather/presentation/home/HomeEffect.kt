package com.aneesh.weather.feature.weather.presentation.home

sealed interface HomeEffect {
    data class ShowToast(val message: String) : HomeEffect
}
