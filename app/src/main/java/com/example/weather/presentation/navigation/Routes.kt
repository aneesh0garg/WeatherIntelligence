package com.example.weather.presentation.navigation

sealed class Routes(val route: String) {

    data object Home : Routes("home")

}