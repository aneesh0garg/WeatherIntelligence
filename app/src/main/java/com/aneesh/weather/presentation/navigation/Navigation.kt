package com.aneesh.weather.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aneesh.weather.presentation.home.HomeScreen

@Composable
fun WeatherNavGraph() {
    val navController = rememberNavController()
    NavHost(
        navController,
        startDestination = Routes.Home.route
    ) {
        composable(Routes.Home.route) {
            HomeScreen()
        }
    }
}