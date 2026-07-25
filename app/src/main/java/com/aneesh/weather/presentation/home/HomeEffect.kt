package com.aneesh.weather.presentation.home

sealed interface HomeEffect {
    data class ShowToast(val message: String) : HomeEffect
}
