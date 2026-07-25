package com.aneesh.weather.feature.weather.presentation.navigation

sealed class Routes(val route: String) {

    data object Home : Routes("home")

}